<?php
// 폴더명 지정
$dir = "./json";
 
// 핸들 획득
$handle  = opendir($dir);

$files = array();
 
// 디렉터리에 포함된 파일을 저장한다.
while (false !== ($filename = readdir($handle))) {
    if($filename == "." || $filename == ".." ||$filename == ".DS_Store"){
        continue;
    }
 
    // 파일인 경우만 목록에 추가한다.
    if(is_file($dir . "/" . $filename)){
    	$fp = fopen("./json/".$filename,"r");
		if( !feof($fp) )
    		$doc_data = fgets($fp);
		fclose($fp);
		$filename_temp = strpos($filename,".json");
		$file_cut_name = substr($filename, 0,$filename_temp);

		$discript = strstr($doc_data, 'explication');
		$discript_temp = $discript;
		$dis_cut_sindex = strpos($discript_temp,":");
		$dis_cut_findex = strpos($discript_temp,".");
		$dis_cut = substr($discript_temp, $dis_cut_sindex+2,$dis_cut_findex - strlen($discript));

		$files[] = array('name' => $file_cut_name,'exp' => $dis_cut);
       
    }
}

$responce= array('set_list' => $files);
 
// 핸들 해제 
closedir($handle);
 

//제이손 만들어서 출력
echo json_encode($responce, JSON_UNESCAPED_UNICODE);
?>