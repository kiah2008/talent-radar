<?php 
echo $this->Form->create('UsersMessage');
	echo $this->Form->input('user_from_id', array('options' => $users));
	echo $this->Form->input('user_to_id', array('options' => $users));
	echo $this->Form->input('content');
	echo $this->Form->submit();
echo $this->Form->end();
?>