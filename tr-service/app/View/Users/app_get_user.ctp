<?php 
echo $this->Form->create('User');
	echo $this->Form->input('id', array('type' => 'select', 'options' => $users));
	echo $this->Form->input('user_request_id', array('type' => 'select', 'options' => $users));
	echo $this->Form->submit();
echo $this->Form->end();
?>