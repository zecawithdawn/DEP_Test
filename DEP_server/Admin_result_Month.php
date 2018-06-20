<?php
    error_reporting(E_ALL);
    ini_set("diplay_errors",1);

    $host = 'localhost';
    $user = 'root';
    $pw = 'root';
    $db = 'depression';
    $port = 3306;

    $json_data = json_decode(file_get_contents('php://input'));
    $result_year = $json_data -> year;
    $result_month = $json_data -> month;
    $result_set = $json_data -> set;

    $sql_results = array();
    
    $mysqli = new mysqli($host,$user,$pw,$db,$port);
    $mysqli->set_charset("utf8");

    if ( !$mysqli ) {
        die("Connect Database failed: " . mysqli_connect_errno());
    };

    $sql = "SELECT ID,score,queset,testmax FROM members NATURAL JOIN results NATURAL JOIN testset WHERE YEAR(time) = '".$result_year."' AND MONTH(time) ='".$result_month."' AND queset = '".$result_set."' GROUP BY ID ORDER BY score DESC";

    $result = $mysqli->query($sql);
    while ($actor = $result->fetch_assoc()) {
        $sql_results[] = array('ID' => $actor['ID'],'score' => (int)$actor['score'],'queset'=> $actor['queset'],'max' => $actor['testmax']);
    }

    $responce = array('sql_result' => $sql_results);
    echo json_encode($responce,JSON_UNESCAPED_UNICODE);

    mysqli_close($mysqli);
?>