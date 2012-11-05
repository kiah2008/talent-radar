<?php 
echo $this->Form->create('UsersBanned');
	echo $this->Form->input('user_from_id', array('options' => $users));
	echo $this->Form->submit();
echo $this->Form->end();
?>