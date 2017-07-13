exports.parse_main = function(file_path){
	var fs = require('fs');
	//var fs1 = require('readline').createInterface({
	//	input:fs.readFile(file_path);
	//input: require('fs').createReadStream(file_path)
	//	});
	try{
		var file1 = new ArrayList;
		file_path.on('line', function (line){
			file1.add(line);});
		file_path.close();
	}catch(err){
		console.log(err);
	}
	return parse_token(file1);
}

exports.parse_token = function(text){
	var result = new ArrayList;
	for (var i =0; i<text.size(); i++){
		var tokens = text.get(i).split('\t');

		var count = tokens.length;

		var subject = new ArrayList;
		for (var j=0; j<count ; j++){
			subject.add(tokens[j]);
		}
		result.add(subject);
	}
	return result;
}

exports.parser = function(text){//text type : ArrayList<ArrayList<String>>
//성적이 나온 과목들을 위한 parser function 
	var ho = new ArrayList;
	for (var i =0; i<text.size(); i++){
		if ( text.get(i).size()==1){
			ho.add(i);
		}}
	for (var i=ho.size()-1;i>=0; i--){
		var index = ho.get(i);
		text.remove(i+1);
		text.remove(i);}
	
	var result = [];
	for (var i =0; i< text.size(); i++){
		var size = text.get(i).size(); //size가 11이면 분반x , 12면 분반o
		var list = text.get(i);
		var myJSON = {'Depart':list.get(1)};
		myJSON['Code']=Number(list.get(2));
		myJSON['Course_no']= list.get(3);
		if (size ==11){
			myJSON['Course_type']=list.get(4);
			myJSON['Course_title']=list.get(5);
			myJSON['Credits']= Number(list.get(6));
			myJSON['AU'] = Number(list.get(7));
			myJSON['Grades'] = list.get(9);
			if (list.get(8)=="N"){
				myJSON['Repeat']=0;
			}else{
				myJSON['Repeat']=1;
			}
		}else{
			myJSON['Course_type'] = list.get(5);
			myJSON['Course_title'] = liste.get(6);
			myJsON['Credits'] = Number(list.get(7));
			myJSON['AU'] = Number(list.get(8));
			myJSON['Grades'] = list.get(10);
			if (list.get(9)=="N"){
				myJSON['Repeat']=0;
			}else{
				myJSON['Repeat']=1;
			}}
		var json2 = switch_type(myJSON);
		result.push(json2);}//myJSON은 jsonobject
	return result;//jsonarray
}
exports.parser2 = function(text){//text type: ArrayList<ArrayList<String>>
//성적이 없는 이번학기 과목들에 대한 jsonarray
	var result = [];
	for (var i =0; i<text.size(); i++){
		var size = text.get(i).size(); 
		var list = text.get(i);
		var json = {'Depart':list.get(2)};
		json['Code']=Number(list.get(3));
		if (size==11){
			json['Course_no']=list.get(4);
			json['Course_type']=list.get(5);
			json['Course_title']=list.get(6);
			json['Credits']=Number(list.get(8));
			json['AU']=Number(list.get(7));
			json['Grades']=Null;
			if (list.get(10)=="N"){
				json['Repeat']=0;
			}else{
				json['Repeat']=1;
			}
		}else{
			json['Course_no']=list.get(5);
			json['Course_type']=list.get(6);
			json['Course_title']=list.get(7);
			json['Credits']=Number(list.get(9));
			json['AU']=Number(list.get(8));
			json['Grades']=Null;
			if (list.get(11)=="N"){
				json['Repeat']=0;
			}else{
				json['Repeat']=1;
			}}
		var json2 = switch_type(json);
		result.push(json2);}//json은 jsonObject
	return result;//jsonarray
}
exports.switch_type = function(json){//과목 분류 코드를 int값으로 바꿔줌
	var course_type = json.get('Course_type');
	var value = 10;

	switch(course_type){
		case "전필" : value = 2;break;
		case "전선" : value = 3;break;
		case "인선" : value = 4;break;
		case "기선" : value = 9;break;
		case "기필" : value = 1;break;
		case "교필" : var subject = json.get('Course_title');
					  var AU = json.get('AU');
					if (AU == 1){
						value = 7;break;
					}else if (AU ==2){
						value = 6;break;//운동
					}else if (AU==0){
						value = 5;break;//영어, 나머지 영어들은 버림.. 
					}else{
						value = 0;break;}
		default : value=8;
	}
	json['Course_type']= value;
	return json;
}
