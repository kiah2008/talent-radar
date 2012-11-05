<?php
class GCMNotificationComponent extends Component {

	public function initialize(&$controller, $settings = array()) {
		$this->controller = $controller;
		$this->key = Configure::read('GCMApiKey');
	}
	
	public function send($devices, $type = null, $message = null, $userId = null, $data = null) {
		
		if(!is_array($devices)) {
			$devices = array($devices);
		}
		
		$notification = json_encode(array('registration_ids' => $devices, 'data' => array('message' => utf8_encode($message), 'type' => $type, 'userId' => $userId, 'data' => $data)));
		$url = "https://android.googleapis.com/gcm/send";
		 
		$headers = array('Authorization: key=' . $this->key, "Content-Type: application/json");
				 
		$x = curl_init($url); 
		curl_setopt($x, CURLOPT_HTTPHEADER, $headers);
		curl_setopt($x, CURLOPT_HEADER, 1); 
		curl_setopt($x, CURLOPT_POST, 1); 
		curl_setopt($x, CURLOPT_POSTFIELDS, $notification); 
		curl_setopt($x, CURLOPT_RETURNTRANSFER, 1); 
		curl_setopt($x, CURLOPT_SSL_VERIFYPEER, false);
		
		$response = curl_exec($x); 
		$http_code = curl_getinfo($x, CURLINFO_HTTP_CODE);
		 
		$header_size = curl_getinfo($x,CURLINFO_HEADER_SIZE);
		$body = substr( $response, $header_size );
		 
		$json_response = json_decode($body);
		$results = $json_response->results;

		// close the curl object
		curl_close($x);
		return $results;
	}

}
