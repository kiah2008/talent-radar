<?php 
echo $this->Form->create('UsersStudy');
	echo $this->Form->input('user_id');
	echo $this->Form->submit();
echo $this->Form->end();
?>