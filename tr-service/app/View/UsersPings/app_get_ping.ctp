<?php 
echo $this->Form->create('UsersPing');
	echo $this->Form->input('id', array('options' => $ids));
	echo $this->Form->input('user_to_id', array('options' => $users));
	echo $this->Form->submit();
echo $this->Form->end();
?>