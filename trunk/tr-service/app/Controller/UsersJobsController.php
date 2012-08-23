<?php
App::uses('AppController', 'Controller');
CakePlugin::load('Linkedin');

class UsersJobsController extends AppController {

	public $name = 'UsersJobs';

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
			
			$user = $this->User->find('first', array('conditions' => array('User.id' => $this->data['UsersJob']['user_id'])));
			if(!empty($user['User']['linkedin_key']) && !empty($user['User']['linkedin_secret']))
			{
				$this->Linkedin->setKeyAndSecretOfUser($user['User']['linkedin_key'], $user['User']['linkedin_secret']);			
				
				$data = $this->Linkedin->call('people/~', array(
														        'positions' => array('title', 'summary', 'start-date', 'end-date', 'is-current', 'company'),
														   ));

				$userJobs = array();
				if(is_array($data['person']) && is_array($data['person']['positions'])) {
					
					if($data['person']['positions']['@total'] > 1)
					{
						foreach($data['person']['positions']['position'] as $userJob) {
							$userJobs[]['user_id'] = $this->data['UsersJob']['user_id'];
							$userJobs[count($userJobs)-1]['title'] = $userJob['title'];
							$userJobs[count($userJobs)-1]['is_current'] = $userJob['is-current'] == 'true' ? 1 : 0;
						}
					}
					else if($data['person']['positions']['@total'] == 1)
					{
						$userJobs[]['user_id'] = $this->data['UsersJob']['user_id'];
						$userJobs[count($userJobs)-1]['title'] = $userJob['title'];
						$userJobs[count($userJobs)-1]['is_current'] = $userJob['is-current'] == 'true' ? 1 : 0;
					}
				}
				
				$this->UsersJob->deleteAll(array('UsersJob.user_id' => $this->data['UsersJob']['user_id']));
				$this->UsersJob->saveAll($userJobs);
							
				$response['result']['status'] = 'ok';
				$response['result']['jobs'] = $userJobs;
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
