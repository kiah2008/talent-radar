<?php 
echo $this->Form->create('Skill');
	echo $this->Form->hidden('skills');
	echo $this->Form->submit();
echo $this->Form->end();
?>