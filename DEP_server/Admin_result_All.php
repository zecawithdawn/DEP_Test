<?php
    error_reporting(E_ALL);
    ini_set("diplay_errors","1");

    $host = 'localhost';
    $user = 'root';
    $pw = 'root';
    $db = 'depression';
    $port = 3306;
    
    $mysqli = new mysqli($host,$user,$pw,$db,$port);
    $mysqli->set_charset("utf8");

    $sql_results = array();

    if ( !$mysqli ) {
        die("Connect Database failed: " . mysqli_connect_errno());
    };

    $sql = "SELECT Year(time),Month(time),AVG(score),queset,testmax FROM results NATURAL JOIN testset GROUP BY Month(time),queset ORDER BY Month(time) DESC";
    $result = $mysqli->query($sql);

    while ($actor = $result->fetch_assoc()) {
        $sql_results[] = array('year' => $actor['Year(time)'],'month' => $actor['Month(time)'], 'avgscore' => (int)$actor['AVG(score)'],'queset'=> $actor['queset'],'max' => $actor['testmax'] );
    }

    $responce = array('sql_result' => $sql_results);
    echo json_encode($responce,JSON_UNESCAPED_UNICODE);
    mysqli_close($mysqli);
?>