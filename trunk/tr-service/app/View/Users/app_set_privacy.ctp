<?php 
echo $this->Form->create('User');
	echo $this->Form->input('id', array('type' => 'select', 'options' => $users));
	echo $this->Form->input('username');
	echo $this->Form->input('show_name');
	echo $this->Form->input('show_headline');
	echo $this->Form->input('show_skills');
	echo $this->Form->input('show_picture');
	echo $this->Form->input('show_in_searches');
	echo $this->Form->submit();
echo $this->Form->end();
?>