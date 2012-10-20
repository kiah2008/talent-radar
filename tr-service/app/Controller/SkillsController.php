<?php
App::uses('AppController', 'Controller');

class SkillsController extends AppController {
	public $name = 'Skills';
	
	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_getSkills');
	}
	
	public function app_getSkills() {
		if(!empty($this->data))
		{
			$response['status'] = 'ok';
			$response['result']['status'] = 'ok';
			$response['result']['skills'] = $this->Skill->find('list', array('fields' => array('Skill.id', 'Skill.name'), 'order' => 'name asc'));
			$this->set('response', $response);
		}
	}
}