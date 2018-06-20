<?php
	error_reporting(E_ALL);
	ini_set("display_errors", 1);


	$host = 'localhost';
    $user = 'root';
    $pw = 'root';
    $db = 'depression';
    $port = 3306;
    
    $mysqli = new mysqli($host,$user,$pw,$db,$port);
    $mysqli->set_charset("utf8");

    if ( !$mysqli ) {
        die("error");
    };

    $json_data = json_decode(file_get_contents('php://input'));
    $result_id = $json_data -> ID;
    $result_score = $json_data -> score;
    $result_set = $json_data -> queset;
    $result_max = $json_data -> maxscore;

    $sql_max = "SELECT testname FROM testset WHERE testname = '".$result_set."'";
    $result2 = $mysqli->query($sql);

    if(strlen($result1) == 0){
        $sql_new_max = "INSERT INTO `depression`.`testset` (`testname`, `testmax`) VALUES ('".$result_set."', '".$result_max."')";
        $result3 = $mysqli->query($sql_new_max);
    } 

	$sql="INSERT INTO `depression`.`results` (`time`, `ID`, `score`,`queset`) VALUES ('".date("Y-m-d")."', '".$result_id."', '".$result_score."', '".$result_set."')";

	$result2 = $mysqli->query($sql);

    if (!$result2) {
        die("error"); 
    }

	mysqli_close($mysqli);
?>