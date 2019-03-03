package com.phq9_final.phq9final;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.compiler.compiler.BPMN2ProcessFactory;
import org.drools.core.marshalling.impl.ProtobufMessages.KnowledgeBase;
import org.kie.api.KieServices;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.kie.internal.builder.KnowledgeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Phq9Integration {

	static Logger log = LoggerFactory.getLogger(Phq9Integration.class);
	
	public static KieContainer initContainer() {
		KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();	
		return kContainer;
	}
	
	private static KieSession createStatefulSession(KieContainer container, final Map<String, Object> createdObjMap) {
		//get stateless session
		KieSession session = container.newKieSession();
		
		session.addEventListener(new RuleRuntimeEventListener() {
			public void objectInserted(ObjectInsertedEvent event) {
				log.info("Object inserted: {}", event.getObject().toString());
				
				if( event.getObject() instanceof PreScreenR ) {
					PreScreenR screenResult = (PreScreenR)event.getObject();
					createdObjMap.put("PreScreenR", screenResult);
					System.out.println( "#########################" + screenResult.getPreScreenResult() );
				}
				
				else if (event.getObject() instanceof PHQ2R) {
					PHQ2R phq2Result = (PHQ2R)event.getObject();
					createdObjMap.put("PHQ2R", phq2Result);
					System.out.println( "#########################" + phq2Result.getPHQ2Result());
				}
			
				else if (event.getObject() instanceof PHQ9R) {
					PHQ9R phq9Result = (PHQ9R)event.getObject();
					createdObjMap.put("PHQ9R", phq9Result);
					System.out.println( "#########################" + phq9Result.getPHQ9Result());
				}
			}

			public void objectUpdated(ObjectUpdatedEvent event) {
				log.info("Object was updated, new Content: {}", event.getObject().toString());
			}

			public void objectDeleted(ObjectDeletedEvent event) {
				log.info("Object retracted: {}", event.getOldObject().toString());
			}
		});
		session.addEventListener(new AgendaEventListener() {
			public void matchCreated(MatchCreatedEvent event) {
				log.info("The rule: {}  can be fired in agenda", event.getMatch().getRule().getName());
			}

			public void matchCancelled(MatchCancelledEvent event) {
				log.info("The rule: {} cannot b in agenda", event.getMatch().getRule().getName());
			}

			public void beforeMatchFired(BeforeMatchFiredEvent event) {
				log.info("The rule: {}  will be fired", event.getMatch().getRule().getName());
			}

			public void afterMatchFired(AfterMatchFiredEvent event) {
				log.info("The rule: {}  has be fired", event.getMatch().getRule().getName());
			}

			public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
				log.info("Agenda group: {}  has be poped", event.getAgendaGroup().getName());
			}

			public void agendaGroupPushed(AgendaGroupPushedEvent event) {
				log.info("Agenda group: {}  has be pushed", event.getAgendaGroup().getName());
			}

			public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
				log.info("Before rule flow group: {}  activated", event.getRuleFlowGroup().getName());
			}

			public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
				log.info("After rule flow group: {}  activated", event.getRuleFlowGroup().getName());
			}

			public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
				log.info("Before rule flow group: {}  deactivated", event.getRuleFlowGroup().getName());
			}

			public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
				log.info("after rule flow group: {}  activated", event.getRuleFlowGroup().getName());
			}
		});

		session.addEventListener(new ProcessEventListener() {
			public void beforeVariableChanged(ProcessVariableChangedEvent arg0) {
				log.info("before variable: {} changed", arg0);
			}

			public void beforeProcessStarted(ProcessStartedEvent arg0) {
				log.info("Process Name: {} has been started", arg0.getProcessInstance().getProcessName());
			}

			public void beforeProcessCompleted(ProcessCompletedEvent arg0) {
				log.info("Process Name: {} has been completed", arg0.getProcessInstance().getProcessName());
			}

			public void beforeNodeTriggered(ProcessNodeTriggeredEvent arg0) {
				log.info("Node Name: {} has been triggered", arg0.getNodeInstance().getNodeName());
			}

			public void beforeNodeLeft(ProcessNodeLeftEvent arg0) {
				// if (arg0.getNodeInstance() instanceof RuleSetNodeInstance){
				log.info("Node Name: {} has been left", arg0.getNodeInstance().getNodeName());
				// }
			}

			public void afterVariableChanged(ProcessVariableChangedEvent arg0) {
				log.info("after variable: {} changed", arg0);
			}

			public void afterProcessStarted(ProcessStartedEvent arg0) {
				log.info("after Process Name: {} has been started", arg0.getProcessInstance().getProcessName());
			}

			public void afterProcessCompleted(ProcessCompletedEvent arg0) {
				log.info("Process Name: {} has stopped", arg0.getProcessInstance().getProcessName());
			}

			public void afterNodeTriggered(ProcessNodeTriggeredEvent arg0) {
				// if (arg0.getNodeInstance() instanceof RuleSetNodeInstance){
				log.info("Node Name: {}  has been entered", arg0.getNodeInstance().getNodeName());
				// }
			}

			public void afterNodeLeft(ProcessNodeLeftEvent arg0) {
				log.info("Node Name: {}  has been left", arg0.getNodeInstance().getNodeName());
			}
		});

		return session;
	}
	
	private void releaseResource(KieContainer container) {
		try {
			if (container != null) {
				container.dispose();
			}
		} catch (Exception e) {
			log.error("Release container error: {}", e.getMessage());
		}
	}
	
	public boolean invokeRules(List<Object> dataObjectList, final List<String> ruleList, Map<String, Object> resultMap) {
		KieContainer container = null;
		boolean success = true;

		try {
			container = initContainer();
			KieSession session = createStatefulSession(container, resultMap);
			
			for (Object dataObj : dataObjectList) {
				session.insert(dataObj);
			}
			
			int firedRules = session.fireAllRules();
			
//			int firedRules = session.fireAllRules(new AgendaFilter() {
//				public boolean accept(Match match) {
//					String ruleName = match.getRule().getName();
//					log.info("check firerule: {}", ruleName);
//					if (ruleName.startsWith("RuleFlow-Split")) {
//						return true;
//					}
//					
//					if (ruleList.contains(ruleName)) {
//						return true;
//					}
//					
//					return false;
//				}
//			});
			log.info("firedRules: Triggered {} rules", firedRules);
		} catch (Exception exp) {
			success = false;
			log.error("Phq2 rule error: ", exp);
		} finally {
			releaseResource(container);
		}
		
		return success;
	}
	
	/**
	 * Compute Pre-Screen result according to patient particular information.
	 * 
	 * @param details Patient particular information.
	 * @return null for low risk group patient; Not null for high risk group patient.
	 */
	public PreScreenR computePreScreenResult(PatientParticular details) {
		PreScreenR result = null;
		ArrayList<Object> inputList = new ArrayList<Object>();
		inputList.add(details);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ArrayList<String> ruleList = new ArrayList<String>();
		ruleList.add("PreScreenRule");
		
		boolean invokeResult = invokeRules(inputList, ruleList, resultMap);
		log.info("computePreScreenResult invoke rule: " + invokeResult);
		
		Object preScreenResult = resultMap.get("PreScreenR");
		if (preScreenResult != null && preScreenResult instanceof PreScreenR) {
			log.info("PreScreenR is found");
			result = (PreScreenR)preScreenResult;
		}
		
		return result;
	}
	
	/**
	 * Compute PHQ2 result.
	 * 
	 * @param p2 PHQ2 answers.
	 * @return Not null for correct cases; Null for invalid cases.
	 */
	public PHQ2R computePHQ2Result(PHQ2Q p2) {
		PHQ2R result = null;
		ArrayList<Object> inputList = new ArrayList<Object>();
		inputList.add(p2);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ArrayList<String> ruleList = new ArrayList<String>();
		ruleList.add("PHQ-2-Rule");
		
		boolean invokeResult = invokeRules(inputList, ruleList, resultMap);
		log.info("computePHQ2Result invoke rule: " + invokeResult);
		
		Object phq2Result = resultMap.get("PHQ2R");
		if (phq2Result != null && phq2Result instanceof PHQ2R) {
			log.info("PHQ2R is found");
			result = (PHQ2R)phq2Result;
		}
		
		return result;
	}
	
	/**
	 * Compute PHQ9 result.
	 * 
	 * @param p2 PHQ2 answers.
	 * @param p2 PHQ2 answers.
	 * @return Not null for correct cases; Null for invalid cases.
	 */
	public PHQ9R computePHQ9Result(PHQ2Q p2, PHQ9Q p9) {
		PHQ9R result = null;
		ArrayList<Object> inputList = new ArrayList<Object>();
		inputList.add(p2);
		inputList.add(p9);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ArrayList<String> ruleList = new ArrayList<String>();
		ruleList.add("PHQ-9-Check-Rule");
		
		boolean invokeResult = invokeRules(inputList, ruleList, resultMap);
		log.info("computePHQ9Result invoke rule: " + invokeResult);
		
		Object phq9Result = resultMap.get("PHQ9R");
		if (phq9Result != null && phq9Result instanceof PHQ9R) {
			log.info("PHQ9R is found");
			result = (PHQ9R)phq9Result;
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		log.info("Logger loaded.");

		try {
		} catch (Exception exp) {
			log.error("Create KJar error", exp);
		}
	}
	
}
