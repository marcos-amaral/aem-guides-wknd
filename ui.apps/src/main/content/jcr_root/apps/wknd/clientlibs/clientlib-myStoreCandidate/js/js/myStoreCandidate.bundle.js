!function(t,e){"use strict";var i={initialValues:{attr1:"valor_attr1"}};myStoreCandidate=function(e,n){this.config=t.extend(!0,{},i,n),this.init(e,this.config);var a=this.config.initialValues;t.extend(!0,void 0,{teste:a})},ContextHub.Utils.inheritance.inherit(myStoreCandidate,ContextHub.Store.PersistedStore),ContextHub.Utils.storeCandidates.registerStoreCandidate(myStoreCandidate,"contexthub.mystorecandidate",0)}(ContextHubJQ);