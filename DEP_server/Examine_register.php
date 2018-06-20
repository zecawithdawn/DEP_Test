<?php
// 폴더명 지정
error_reporting(E_ALL);
ini_set("display_errors", 1);

//POST input
$json_data = json_decode(file_get_contents('php://input'));
$register_ID = $json_data -> ID;
$register_PW = $json_data -> PW;
$register_COM = $json_data -> COM;

//DB 설정및 연결
$host = 'localhost';
$user = 'root';
$pw = 'root';
$db = 'depression';
$port = 3306;

$mysqli = new mysqli($host,$user,$pw,$db,$port);
$mysqli->set_charset("utf8");

if ( !$mysqli ) {
    die("Connect Database failed: " . mysqli_connect_errno());
};
//이메일 폼인지 확인

if(filter_var($ID, FILTER_VALIDATE_EMAIL)){
	$sql = "INSERT INTO `depression`.`members` (`ID`, `password`, `admin`, `company`) VALUES ('".$register_ID."', '".register_PW."', '0', NULL)";
$result = $mysqli->query($sql);

if ($result) {
    echo json_encode(array('server_responce' => "1"), JSON_UNESCAPED_UNICODE);
}else{
	echo json_encode(array('server_responce' => "3"), JSON_UNESCAPED_UNICODE);
}
}else{
	echo json_encode(array('server_responce' => "2"), JSON_UNESCAPED_UNICODE);
}

mysqli_close($mysqli);

?>