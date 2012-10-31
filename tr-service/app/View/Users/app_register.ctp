<?php 
echo $this->Form->create('User');
	echo $this->Form->input('email');
	echo $this->Form->input('password');
	echo $this->Form->input('name');
	echo $this->Form->input('surname');
	echo $this->Form->input('headline');
	echo $this->Form->input('username');
	echo $this->Form->input('show_name');
	echo $this->Form->input('show_headline');
	echo $this->Form->input('show_skills');
	echo $this->Form->input('show_picture');
	echo $this->Form->input('show_in_searches');
	echo $this->Form->input('skills', array('name' => 'data[UsersSkill][]'));
	echo $this->Form->input('skills', array('name' => 'data[UsersSkill][]'));
	echo $this->Form->submit();
echo $this->Form->end();
?>