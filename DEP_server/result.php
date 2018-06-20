<!DOCTYPE html>

<html>	
<head>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
    if ( !$mysqli ) {
        die("Connect Database failed: " . mysqli_connect_errno());
    }
    
    //$sql="SELECT * FROM company";
    //$head = $mysqli -> query($sql);

   // $actor = $head->fetch_assoc()
    //   echo "<h1>".$actor['company']."의 데이터베이스 정보입니다.<h1>";
	?> 
</head>
<body>
<table>
	<tr><th>이름</th><th>아이디</th><th>질문셋트</th><th>점수</th><th>설문날짜</th></tr>	
	//<?php
	//$sql="SELECT name,ID,queset,score,time FROM results ORDER BY time DESC";
	//$table = $mysqli -> query($sql);
	
	//while ($actor = $table->fetch_assoc()) {
	//	echo "<tr><td>".$actor['name']."</td><td>".$actor['ID']."</td><td>".$actor['queset']."</td><td>".$actor['score']."</td><td>".$actor['time']."</td></tr>";
	//}
	?>
</table>
<ul class="nav nav-tabs">
  <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
  <li><a data-toggle="tab" href="#menu1">Menu 1</a></li>
  <li><a data-toggle="tab" href="#menu2">Menu 2</a></li>
</ul>

<div class="tab-content">
  <div id="home" class="tab-pane fade in active">
    <h3>HOME</h3>
    <p>Some content.</p>
  </div>
  <div id="menu1" class="tab-pane fade">
    <h3>Menu 1</h3>
    <p>Some content in menu 1.</p>
  </div>
  <div id="menu2" class="tab-pane fade">
    <h3>Menu 2</h3>
    <p>Some content in menu 2.</p>
  </div>
</body>
<? mysqli_close($mysqli);?>	
</html>