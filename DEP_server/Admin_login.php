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

    $sql1 = "SELECT `admin` FROM members WHERE `ID` = '".$login_id."' AND `password` = '".$login_pw."'";
    $result1 = $mysqli->query($sql1);

    if (($actor = $result1->fetch_assoc())) {
    	// 로그인 성공 
        //팀장급 1 사장급 2 팀원 0
        if ($actor['admin'] == '1') {
            echo json_encode(array('sever_responce' => '1'));
        }elseif ($actor['admin'] == '2') {

            echo json_encode(array('sever_responce' => '2');
        }else{
            echo json_encode(array('sever_responce' => '3'));
        }    	
	} else { 	
        //실패 2
        echo json_encode(array('sever_responce' => '4'));  	
    }

    mysqli_close($mysqli);

?>