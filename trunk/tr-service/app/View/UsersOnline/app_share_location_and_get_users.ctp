<?php 
echo $this->Form->create('UsersOnline');
	echo $this->Form->input('user_id');
	echo $this->Form->input('latitude');
	echo $this->Form->input('longitude');
	echo $this->Form->input('duration');
	echo $this->Form->submit();
echo $this->Form->end();
?>