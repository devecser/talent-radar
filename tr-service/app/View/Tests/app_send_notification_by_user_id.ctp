<?php 
echo $this->Form->create('User');
	echo $this->Form->input('id', array('type' => 'select', 'options' => $ids));
	echo $this->Form->input('message', array('type' => 'text'));
	echo $this->Form->submit();
echo $this->Form->end();
?>