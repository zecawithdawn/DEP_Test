<?php
error_reporting(E_ALL);
ini_set("display_errors", 1);

$host = 'localhost';
$user = 'root';
$pw = 'root';
$db = 'depression';
$port = 3306;

/////////
//db에 관리자가 저장해놓은 질문셋트의 이름을 가져와
$mysqli = new mysqli($host,$user,$pw,$db,$port);
$mysqli->set_charset("utf8");

$sql_results = array();
if ( !$mysqli ) {
    die("Connect Database failed: " . mysqli_connect_errno());
};
$dir = './json';

$sql = "SELECT curque FROM `current_queset`";
$result = $mysqli->query($sql);
echo "0";
if($actor = $result->fetch_assoc()){
    //파일을 하나씩 확인하면서 name이 관리자가 설정해 놓은거랑 맞는지 확인해
    $data = null;
    $handle  = opendir($dir);
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

            if ($file_cut_name == $actor['curque']) {      
          //db에 저장된 거랑 같은거면 json을 보내야해
                $fp2 = fopen("./json/".$filename,"r");
                while (($buffer = fgets($fp2, 4096)) !== false) {
                $data=$data.$buffer;
                }
 fclose($fp2);
            echo json_encode(array('server_responce' => '1','testset' => $data),JSON_UNESCAPED_UNICODE);
            }
           
        }
    }

}else{

    echo json_encode(array('server_responce' => '2'),JSON_UNESCAPED_UNICODE);
}
mysqli_close($mysqli);

?>