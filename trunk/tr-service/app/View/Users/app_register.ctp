<?php 
echo $this->Form->create('User');
	echo $this->Form->input('email');
	echo $this->Form->input('username');
	echo $this->Form->input('password');
	echo $this->Form->input('name');
	echo $this->Form->input('surname');
	echo $this->Form->input('headline');
	echo $this->Form->submit();
echo $this->Form->end();
?>