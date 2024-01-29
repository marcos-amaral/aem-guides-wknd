(function ($, window) {
    'use strict';

    var data;

    var defaultConfig = {
        initialValues: {
            attr1: 'valor_attr1'
        }
    };

    myStoreCandidate = function (name, config) {
        this.config = $.extend(true, {}, defaultConfig, config);
        this.init(name, this.config);

        /* initial location */
        var initialValues = this.config.initialValues;

        $.extend(true, data, {
            teste: initialValues
        });
        
    };
    ContextHub.Utils.inheritance.inherit(myStoreCandidate, ContextHub.Store.PersistedStore);

    ContextHub.Utils.storeCandidates.registerStoreCandidate(myStoreCandidate, 'contexthub.mystorecandidate', 0);
}(ContextHubJQ, this)); 
