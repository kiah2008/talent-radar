<!DOCTYPE html>
<html>
<head>
	<?php echo $this->Html->charset(); ?>
	<?php echo $this->Html->css('styles')?>
	<title>
	</title>
</head>
<body>
	<header>
		<?php echo $this->Html->link($this->Html->image('logo.png'), array('controller' => 'pages', 'action' => 'index'), array('id' => 'logo', 'escape' => false))?>
	</header>
	
	<?php echo $this->Session->flash(); ?>
	<?php echo $this->fetch('content'); ?>
	<?php echo $this->element('sql_dump'); ?>
</body>
</html>
