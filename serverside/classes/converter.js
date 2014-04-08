var fs = require('fs');
var path = require('path');
var mkdirp = require('mkdirp');
var avconv = require('avconv');
/*
 * "constructor" function implements all need variable initialization 
 */
//function Converter (camera_files, screen_captures, log, output ) {
//
//	// if camera_files is array just add it
//	if ( typeof camera_files === '[object Array]' ) {
//		this.camera_files = camera_files;
//	}
//
//	// if camera_files is a string, we create an array and add em to it.
//	if ( typeof camera_files === '[object String]') {
//		this.camera_files = [];
//		this.camera_files.push(camera_files);
//	}
//
//	// output shouldnt be undefined, should be an not empty string
//	this.output = (typeof output === 'string' && output != '') ? output : null;
//
//	// screen captures should be an array > 0
//	this.screen_captures = (typeof screen_captures === 'array') ? screen_captures : null;
//
//	// logs should be an json (object)
//	this.logs = (typeof log === 'object') ? log : null;
//}

function Converter() {

}

/*
 * Getters and Setters
 * TODO add type check
 */
Converter.prototype = {
	// Camera File Getters Setters

	get cameraFiles() {
		return this._camera_file;
	},
	
	set cameraFiles(path) {
		this._camera_file = path;
	},
	
	// screen captures 
	get screenCaptures() {
		return this._screen_captures;
	},
	
	set screenCaptures(screens) {
		this._screencaptures = screens;
	},
	
	// logs
	get logs() {
		return this._logs;
	},
	
	set logs(logs) {
		this._logs = logs;
	},
	
	// output
	get output() {
		return this._camera_file;
	},
	
	set output(path) {
		this._camera_file = path;
	}
}

Converter.prototype.addCameraFile = function(camera_file) {

	if ( typeof camera_files === '[object String]') {
		this.camera_files = [];
		this.camera_files.push(camera_files);
	}

}

Converter.prototype.addScreenshot = function(screenshot) {
	this.screenCaptures.push(screenshot);
}

/**
 *
 * creates a video file from screenshots and input videos
 *  
 *
 */
Converter.prototype.createResult = function(user, session, callback) {
    callback();
    mkdirp.sync('./uploads/'+user+'/'+session +'/results/captures/');
    
    // parameters for each image
    var paramDict = [];
    var input = './uploads/' + user + '/'+ session +'/captures/';
    // read captures directory
    fs.readdir(input, function(err, data){
        if(err){
            throw err;
        }

        // files read, create list of intervalls and files to merge
        var count = 0;
        data.forEach(function(file) {

           if( file != '.DS_Store') {
               //console.log(file);

               var time = file.split('.')[0];

               // param object stored in paramDict
               var param = {
                   stamp: time,
                   filename: file


               };

               paramDict.push(param);
               count++;
           }
        });

        // now create a video from each param object
        var finCount = 0;
        for (var i = 0; i < paramDict.length; i++ ) {

            var output = './uploads/' + user + '/'+ session +'/results/captures/'+ (i+1)+'.mpg';
            var intervall = 0;
            if (i == paramDict.length-1) {
               intervall = 4;
            } else {
                var tmp = Math.floor((parseInt(paramDict[i+1].stamp) - parseInt(paramDict[i].stamp)) / 1000);
                //console.log("tmp: "+ tmp);
                intervall = tmp;
            }

            var input = './uploads/' + user + '/'+ session +'/captures/' + paramDict[i].filename;
            var paramProc = [
                '-f', 'image2',
                '-loop', '1',
                '-i', input,
                '-s', '720x1280',
                '-t', intervall,
                '-y',
               output
            ];


            var stream = avconv(paramProc);

            stream.once('end', function(exitCode, signal){

                if (finCount == paramDict.length-1) {

                    var dir = './uploads/'+user+'/'+session+'/results/captures/'; //
                    var path = require("path");
                    var videopaths = '';

                    fs.readdir(dir, function (err, files) {
                        if (err) {
                            mkdirp.sync(dir);
                            files = fs.readdirSync(dir);
                        }
                        var pushed = 0;
                        files.forEach(function (file) {

                            if(file != '.DS_Store' && file != 'result.mpg') {

                                if (pushed > 0) {
                                    videopaths += "\|"+dir+file;
                                } else {
                                    videopaths += dir+file;
                                }
                                pushed++;

                            }

                        });

                        var imgoutput = dir + 'result.mpg';
                        //params here
                        var params  = [
                            '-i', 'concat:'+videopaths,
                            '-c', 'copy',
                            '-y',
                            imgoutput
                        ];

                        var stream = avconv(params);

                        stream.once('end', function(exitCode, signal) {

                            // errorhandling

                            // produce video
                            var dir = './uploads/'+user+'/'+session+'/videos/'; //
                            var path = require("path");
                            var videopaths = '';

                            fs.readdir(dir, function (err, files) {
                                if (err) {
                                    throw err;
                                }
                                var pushed = 0;
                                files.forEach(function (file) {
                                    //console.log("%s (%s)", file, path.extname(file));
                                    if(file != '.DS_Store') {
                                        videopaths += dir+file;
                                    }

                                });

                                var output = './uploads/'+user+'/'+session+'/results/pad.mpg';
                                //params here
                                var params  = [
                                    '-i', videopaths,
                                    '-filter:v', 'transpose=1, transpose=1, transpose=1, pad=2*iw',
                                    '-strict', 'experimental',
                                    '-y',
                                    output
                                ];

                                var stream = avconv(params);

                                stream.once('end', function(exitCode, signal) {


                                    var input = output;
                                    var filter ="movie="+ imgoutput +" [wm];[in][wm] overlay=720:0 [out]";
                                    var finaloutput = './uploads/'+user+'/'+session+'/results/result.mpg';
                                    var params = [
                                        '-i', input,
                                        '-vf', filter,
                                        '-strict', 'experimental',
                                        '-y',
                                        finaloutput
                                    ];

                                    var stream = avconv(params);

                                    stream.pipe(process.stdout);

                                    stream.once('end', function(exitCode, signal) {
                                        console.log('all fin '+ exitCode );

                                        var qstr = 'INSERT INTO results (file, timestamp, sessions_idsessions VALUES' +
                                            '("'+ finaloutput + '", NOW(),' + session + ')';

                                        var query = new Query;

                                        query.execute(qstr, function(rows) {
                                            console.log(rows);


                                        });

                                    });


                                });
                            });
                        });

                    });
                } else {
                    finCount++;
                }
            });
        }
    });
}

///**
// * merges all videos files from session
// */
//Converter.prototype.mergeVideoInput = function(user, session) {
//
//	// avconv -i concat:file1.mp4\|file2.mp4 -c copy output.mp4
//	var dir = '/uploads/'+user+'/'+session+'/video/'; //
//
//	var path = require("path");
//	var videopaths = [];
//
//	fs.readdir(dir, function (err, files) {
//	    if (err) {
//	        throw err;
//	    }
//	    var pushed = 0;
//	    files.map(function (file) {
//	        return path.join(p, file);
//	    }).filter(function (file) {
//	        return fs.statSync(file).isFile();
//	    }).forEach(function (file) {
//	        console.log("%s (%s)", file, path.extname(file));
//	        if (path.extname(file) == 'mp4') {
//	        	if (pushed > 0) {
//	        		videopaths.push('|'+file);
//	        	} else {
//	        		videopaths.push(file);
//	        	}
//	        	pushed++;
//	        }
//	    });
//	});
//
//	//params here
//	var params  = [
//		'-i', 'concat:'+videopaths,
//		'-c', 'copy',
//		'result.mp4'
//	];
//
//	 var stream = avconv(params);
//
//	stream.on('data', function(data) {
//	    // handling
//	});
//
//	stream.on('progress', function(progress) {
//	    /*
//	    Progress is a floating number between 0 ... 1 that keeps you
//	    informed about the current avconv conversion process.
//	    */
//	});
//
//	stream.once('end', function(exitCode, signal) {
//		console.log('fin ' + exitCode);
//		// errorhandling
//	});
//}
//
///*
// * converts video files into one resultsvideo
// *
// */
//Converter.prototype.createResults = function () {
//	// TODO implementation
//
//};

module.exports = Converter;