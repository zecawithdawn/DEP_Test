<?php 

	error_reporting(E_ALL);
	ini_set("display_errors", 1);

	$host = 'localhost';
    $user = 'root';
    $pw = 'root';
    $db = 'depression';
    $port = 3306;

    $json_data = json_decode(file_get_contents('php://input'));
    $login_id = $json_data -> ID;
    $login_pw = $json_data -> password;

    $mysqli = new mysqli($host,$user,$pw,$db,$port);
    $mysqli->set_charset("utf8");

    $sql1 = "SELECT * FROM members WHERE `ID` = '".$login_id."' AND `password` = '".$login_pw."'";  
    $result1 = $mysqli->query($sql1);

    if ($actor = $result1->fetch_assoc()) {
    	// 로그인 성공 
    	echo json_encode(array('sever_responce' => '1'));
	} else {
    	
    	$sql2 = "SELECT `company` FROM members WHERE `ID` = '".$login_id."'";
    	$result2 = $mysqli->query($sql2);
    	
    	if ($actor2 = $result2->fetch_assoc()) {
    		//아이디 없음
    		echo json_encode(array('sever_responce' => '2'));
    	}else{
    		//비밀번호 틀림
    		echo json_encode(array('sever_responce' => '3'));
    	}
    }

    mysqli_close($mysqli);

?>