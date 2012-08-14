<?php 
echo $this->Form->create('User');
	echo $this->Form->input('id', array('type' => 'select'));
	echo $this->Form->input('android_device_id', array('type' => 'text'));
	echo $this->Form->submit();
echo $this->Form->end();
?>