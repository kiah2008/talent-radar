<?php 
echo $this->Form->create('UsersMessage');
	echo $this->Form->input('user1_id', array('options' => $users));
	echo $this->Form->input('user2_id', array('options' => $users));
	echo $this->Form->input('limit', array('options' => array(0 => 'Todos', 2 => 2, 5 => 5, 10 => 10, 20 => 20, 30 => 30)));
	echo $this->Form->input('first_message_id', array('type' => 'text'));
	echo $this->Form->submit();
echo $this->Form->end();
?>