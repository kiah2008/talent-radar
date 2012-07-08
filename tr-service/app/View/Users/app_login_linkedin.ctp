<?php 
echo $this->Form->create('User');
	echo $this->Form->input('token_linkedin');
	echo $this->Form->submit();
echo $this->Form->end();
?>