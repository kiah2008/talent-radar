<?php
App::uses('AppController', 'Controller');
CakePlugin::load('Linkedin');

class UsersStudiesController extends AppController {

	public $name = 'UsersStudies';

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
			
			$user = $this->User->find('first', array('conditions' => array('User.id' => $this->data['UsersStudy']['user_id'])));
			if(!empty($user['User']['linkedin_key']) && !empty($user['User']['linkedin_secret']))
			{
				$this->Linkedin->setKeyAndSecretOfUser($user['User']['linkedin_key'], $user['User']['linkedin_secret']);			
				
				$data = $this->Linkedin->call('people/~', array(
														        'educations' => array('degree', 'school-name'),
														   ));
				$userStudies = array();
				if(is_array($data['person']) && is_array($data['person']['educations'])) {
					if($data['person']['educations']['@total'] > 1)
					{
						foreach($data['person']['educations']['education'] as $userStudy) {
							$userStudies[]['user_id'] = $this->data['UsersStudy']['user_id'];
							$userStudies[count($userStudies)-1]['title'] = $userStudy['degree'];
							$userStudies[count($userStudies)-1]['establishment'] = $userStudy['school-name'];
						}
					}
					else if($data['person']['educations']['@total'] == 1)
					{
						$userStudies[]['user_id'] = $this->data['UsersStudy']['user_id'];
						$userStudies[count($userStudies)-1]['title'] = $data['person']['educations']['education']['degree'];
						$userStudies[count($userStudies)-1]['establishment'] = $data['person']['educations']['education']['school-name'];
					}
				}
				
				$this->UsersStudy->deleteAll(array('UsersStudy.user_id' => $this->data['UsersStudy']['user_id']));
				$this->UsersStudy->saveAll($userStudies);
							
				$response['result']['status'] = 'ok';
				$response['result']['jobs'] = $userStudies;
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
