<?php 
echo $this->Form->create('UsersMessage');
	echo $this->Form->input('user1_id', array('options' => $users));
	echo $this->Form->input('user2_id', array('options' => $users));
	echo $this->Form->submit();
echo $this->Form->end();
?>