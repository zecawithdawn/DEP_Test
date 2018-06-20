
<?php
// 폴더명 지정
error_reporting(E_ALL);
ini_set("display_errors", 1);

$json_data = json_decode(file_get_contents('php://input'));
$file = $json_data -> file;
$file_name = $json_data -> file_name;

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

$sql = "UPDATE `current_queset` SET `curque` ='".$file."' WHERE 1";
$result = $mysqli->query($sql);

$handle = @fopen("./json/".$file_name, "r");
$data = null;
if ($handle) {
    while (($buffer = fgets($handle, 4096)) !== false) {
        $data=$data.$buffer;
    }
    if (!feof($handle)) {
        echo "Error: unexpected fgets() fail\n";
    }
    fclose($handle);
}
mysqli_close($mysqli);
echo $data;
?>