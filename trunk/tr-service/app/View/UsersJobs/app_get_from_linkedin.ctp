<?php 
echo $this->Form->create('UsersJob');
	echo $this->Form->input('user_id');
	echo $this->Form->submit();
echo $this->Form->end();
?>