package de.hsosnabrueck.hipsterbility.persistence.impl;

import de.hsosnabrueck.hipsterbility.entities.FileEntity;
import de.hsosnabrueck.hipsterbility.exceptions.DataAccessException;
import de.hsosnabrueck.hipsterbility.persistence.FileDao;
import de.hsosnabrueck.hipsterbility.util.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Albert on 01.10.2014.
 */
@Singleton
public class FileDaoImpl extends GenericDaoImpl<FileEntity, Integer> implements FileDao {

    protected FileDaoImpl() {
        super(FileEntity.class, FileEntity.TABLE_NAME);
    }

    @Override
    public FileEntity read(Integer id) throws DataAccessException {
        FileEntity fileEntity = super.read(id);
        if(null == fileEntity){
            return null;
        }
        File file = new File(fileEntity.getFilePath());
        try {
            FileInputStream fis = new FileInputStream(file);
            fileEntity.setData(IOUtils.toByteArray(fis));
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataAccessException(e);
        }
        return fileEntity;
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        FileEntity fileEntity = super.read(id);
        if(null == fileEntity){
            throw new DataAccessException("File does not exist.");
        }
        File file = new File(fileEntity.getFilePath());
        if(!file.exists() || file.delete()){
            super.delete(id);
        } else {
            throw new DataAccessException("File could not be deleted.");
        }
    }

    @Override
    public FileEntity create(FileEntity fileEntity) throws DataAccessException {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(fileEntity);
            saveToFile(fileEntity);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error saving file " + type.getSimpleName() + ": " + e.getMessage());
            throw new DataAccessException(e);
        } finally {
            em.close();
        }
        return fileEntity;
    }

    private void saveToFile(FileEntity fileEntity) throws IOException {
        File file = new File(createPathForFile(fileEntity));
        FileOutputStream fos = new FileOutputStream(file);
        IOUtils.write(fileEntity.getData(), fos);
    }

    @Override
    public FileEntity createIfNotExists(FileEntity fileEntity, Integer id) throws DataAccessException {
        FileEntity dbFileEntity = read(id);
        if(null == dbFileEntity){
            dbFileEntity = create(fileEntity);
        }
        return dbFileEntity;
    }

    @Override
    public FileEntity update(Integer id, FileEntity fileEntity) throws DataAccessException {
        try {
            saveToFile(fileEntity);
            return super.update(id, fileEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException(e);
        }
    }

    private String createPathForFile(FileEntity fileEntity){
        StringBuffer buffer = new StringBuffer();
        buffer.append(Utils.FILE_PATH).append(File.separator); // base path
        buffer.append(fileEntity.getType().getName()).append(File.separator); // file type directory
        buffer.append(fileEntity.getId()).append(fileEntity.getFileExtension()); // file name consists of id and extension
        return buffer.toString();
    }

}
