<?php //echo json_encode($response, JSON_FORCE_OBJECT);?>
<?php
function arrayToObject($array) {
	if(!is_array($array)) {
		return $array;
	}

	$object = new stdClass();
	if (count($array) > 0) {
		foreach ($array as $name=>$value) {
			if (isset($name)) {
				$object->$name = arrayToObject($value);
			}
		}
	}
	
	return $object;
}
echo json_encode(arrayToObject($response));
?>