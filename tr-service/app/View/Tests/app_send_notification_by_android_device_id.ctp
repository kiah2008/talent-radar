<?php 
echo $this->Form->create('User');
	echo $this->Form->input('android_device_id', array('type' => 'text'));
	echo $this->Form->input('message', array('type' => 'text'));
	echo $this->Form->submit();
echo $this->Form->end();
?>