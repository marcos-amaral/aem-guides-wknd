(function ($, window) {
    var defaultConfig = {};

    myStoreCandidate = function (name, config) {
        this.config = $.extend(true, {}, defaultConfig, config);
        this.init(name, this.config);
    };
    ContextHub.Utils.inheritance.inherit(myStoreCandidate, ContextHub.Store.PersistedStore);
    ContextHub.Utils.storeCandidates.registerStoreCandidate(myStoreCandidate, 'mystorecandidate', 0);
}(ContextHubJQ, this)); 
