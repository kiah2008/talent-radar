<?php 
echo $this->Form->create('User');
	echo $this->Form->input('email');
	echo $this->Form->input('username');
	echo $this->Form->input('password_1', array('type' => 'password'));
	echo $this->Form->input('password_2', array('type' => 'password'));
	echo $this->Form->input('name');
	echo $this->Form->input('surname');
	echo $this->Form->submit();
echo $this->Form->end();
?>