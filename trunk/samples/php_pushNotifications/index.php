<?php 

if($_GET['option'] == 2) {
$messageText = 'asdsad';
	$collapseKey = 'asdasdasdads';
		
	$yourKey = 'AIzaSyBd0K-XNCdkNHsJ2ra0wjNCAe_s2ojfZWM';    
	$headers = array('Authorization:key=' . $yourKey);    
	$data = array(    
            'registration_id' => '830003729128',    
            'collapse_key' => $collapseKey,    
            'data.message' => $messageText);  
        
	$ch = curl_init();    
        
	curl_setopt($ch, CURLOPT_URL, "https://android.googleapis.com/gcm/send");    
    //if ($headers)    
	curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);    
	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);    
	curl_setopt($ch, CURLOPT_POST, true);    
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);    
	curl_setopt($ch, CURLOPT_POSTFIELDS, $data);    
        
	$response = curl_exec($ch);    
	$httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);    
	if (curl_errno($ch)) {    
		//request failed
		echo "error1";
	}    
	if ($httpCode != 200) {    
		//request failed 
		echo "error2";   
	}    
	curl_close($ch);    
	echo "<pre>";var_dump($response);echo "</pre>";
}        
else 
{
	$verbose = true;
	$data = array('ads' => 'Hola');	
	$devices = array('830002611240');
	if ($verbose==true) {
		echo "The data array is:<br />";
		print_r($data);
		echo "<br />";
		echo "The GCM devices array is:<br />";
		print_r($devices);
		echo "<br />";
	}
	$message = json_encode(array("registration_ids"=>$devices, 
		"data"=>$data));
	if ($verbose==true) echo "JSON message:<br />";
	if ($verbose==true) echo "$message" . "<br />";
	if ($verbose==true) echo "Sending over GCM<br />";
	 
	// My GCM API key. KEEP SECRET!!!!!
	$phcaApiKey = "AIzaSyBd0K-XNCdkNHsJ2ra0wjNCAe_s2ojfZWM"; // SECRET!!!
	 
	$url = "https://android.googleapis.com/gcm/send";
	 
	$headers = array('Authorization: key=' . $phcaApiKey, "Content-Type: application/json");
	
	if ($verbose==true) echo "Sending POST to GCM server. <br />";
	 
	$x = curl_init($url); 
	curl_setopt($x, CURLOPT_HTTPHEADER, $headers);
	curl_setopt($x, CURLOPT_HEADER, 1); 
	curl_setopt($x, CURLOPT_POST, 1); 
	curl_setopt($x, CURLOPT_POSTFIELDS, $message); 
	curl_setopt($x, CURLOPT_RETURNTRANSFER, 1); 
	curl_setopt($x, CURLOPT_SSL_VERIFYPEER, false);
	$response = curl_exec($x); 
	$http_code = curl_getinfo($x, CURLINFO_HTTP_CODE);
	 
	// request has been sent. Now get the response. The response 
	// is in the form of a JSON object, formatted as
	/*
		{ "multicast_id": 216,
		  "success": 3,
		  "failure": 3,
		  "canonical_ids": 1,
		  "results": [
		    { "message_id": "1:0408" },
		    { "error": "Unavailable" },
		    { "error": "InvalidRegistration" },
		    { "message_id": "1:1516" },
		    { "message_id": "1:2342", "registration_id": "32" },
		    { "error": "NotRegistered"}
		  ]
		}
	*/
	 
	$header_size = curl_getinfo($x,CURLINFO_HEADER_SIZE);
	$body = substr( $response, $header_size );
	 
	$json_response = json_decode($body);
	$results = $json_response->results;
	 
	// first, print some debugging information
	if ($verbose==true) {
		echo "The response from the C2DM server was:<br />";
		echo "<pre>";var_dump($response);echo "</pre>";
		echo "<br />";
		$header = substr($response, 0, $header_size);
		echo "Header: $header<br />\n";
		echo "Body: $body<br />\n";
		echo "JSON decoded response:<br />";
		echo "<pre>";var_dump($json_response);echo "</pre>";
		echo "<br />";
		echo "Results:<br />";
		echo "<pre>";var_dump($results);echo "</pre>";
		echo "<br />";
	}
	// close the curl object
	curl_close($x);
}
?>