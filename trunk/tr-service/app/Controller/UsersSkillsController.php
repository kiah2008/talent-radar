<?php
App::uses('AppController', 'Controller');
CakePlugin::load('Linkedin');

class UsersSkillsController extends AppController {

	public $name = 'UsersSkills';

	var $components = array('Linkedin.Linkedin');
	
	public function beforeFilter() {
		parent::beforeFilter();
		
		$this->Auth->allow('app_getFromLinkedin');
	}
	
	public function app_getFromLinkedin()
	{
		$this->loadModel('User');
		if(!empty($this->data))
		{
			$response['status'] = 'ok';
			$response['result']['status'] = 'error';
			
			$user = $this->User->find('first', array('conditions' => array('User.id' => $this->data['UsersSkill']['user_id'])));
			if(!empty($user['User']['linkedin_key']) && !empty($user['User']['linkedin_secret']))
			{
				$this->Linkedin->setKeyAndSecretOfUser($user['User']['linkedin_key'], $user['User']['linkedin_secret']);			
				
				$data = $this->Linkedin->call('people/~', array(
														        'skills' => array('id', 'skill', 'proficiency', 'years'),
														   ));
				$userSkills = array();
				if(is_array($data['person']) && is_array($data['person']['skills'])) {
					
					if($data['person']['skills']['@total'] > 1)
					{
						foreach($data['person']['skills']['skill'] as $userSkill) {
							$userSkills[] = $userSkill['skill']['name'];
						}
					}
					else
					{
						$userSkills[] = $data['person']['skills']['skill']['skill']['name'];
					}
				}
							
				$this->loadModel('Skill');
				$skills = $this->Skill->find('list', array('fields' => array('id', 'name')));
				$skillsToSave = array_diff($userSkills, $skills);
				
				if(!empty($skillsToSave)) {
					foreach($skillsToSave as $i => $v) {
						$skillsToSaveDB[] = array('name' => $v);
					}
					$this->Skill->create();
					$this->Skill->saveAll($skillsToSaveDB);
				}
				
				$userSkills = $this->Skill->find('list', array('conditions' => array('Skill.name' => $userSkills), 'fields' => array('id', 'name')));
				$this->UsersSkill->deleteAll(array('UsersSkill.user_id' => $this->data['UsersSkill']['user_id']));
				if(!empty($userSkills)) {
					foreach($userSkills as $k => $v) {
						$userSkillsDB[] = array('user_id' => $this->data['UsersSkill']['user_id'], 'skill_id' => $k);
					}
					$this->UsersSkill->saveAll($userSkillsDB);
				}
				
				$response['result']['status'] = 'ok';
				$response['result']['skills'] = $userSkills;
			}
			else
			{
				$response['result']['status'] = 'error';
				$response['result']['message'] = __('There isn\'t a Linkedin account associated', true);
			}
			
			$this->set('response', $response);
		}
		else
		{
			$this->set('users', $this->User->find('list', array('fields' => array('id', 'id'))));
		}
	}
}
