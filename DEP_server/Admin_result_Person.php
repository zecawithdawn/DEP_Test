 <?php
    error_reporting(E_ALL);
    ini_set("diplay_errors","1");

    $host = 'localhost';
    $user = 'root';
    $pw = 'root';
    $db = 'depression';
    $port = 3306;
    

    $json_data = json_decode(file_get_contents('php://input'));
    $result_id = $json_data -> ID;
    $result_set = $json_data -> set;

    $sql_results = array();

    $mysqli = new mysqli($host,$user,$pw,$db,$port);
    $mysqli->set_charset("utf8");

    $sql1 = "SELECT Year(time),Month(time),AVG(score),queset,testmax FROM results NATURAL JOIN testset WHERE id='".$result_id."' AND queset = '".$result_set."' GROUP BY Month(time) ORDER BY Month(time) DESC";
    $result = $mysqli->query($sql1);

    while ($actor = $result->fetch_assoc()) {
        $sql_results[] = array('year' => $actor['Year(time)'],'month' => $actor['Month(time)'], 'score' => (int)$actor['AVG(score)'],'queset'=> $actor['queset'],'max' => $actor['testmax'] );
    }

    $responce = array('sql_result' => $sql_results);

    echo json_encode($responce,JSON_UNESCAPED_UNICODE);

    mysqli_close($mysqli);

?>
