<?php 
echo $this->Html->link('Descargar BETA', '/files/tr-mobile-app.apk', array('class' => 'download-beta'));
?>
<?php 
echo $this->Html->link($this->Html->image('qr_app.png'), '/files/tr-mobile-app.apk', array('escape' => false, 'class' => 'qr-beta'));
?>
