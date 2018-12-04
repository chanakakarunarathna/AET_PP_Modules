/**
 * Created by supun.s on 06/08/2015.
 */
(function(module) {

    module.service("HierarchyMapService", ['$log', 'mapModalService', 'mapService', '$rootScope', '$state', '$timeout', 'localStorageService', 'modalService',
        /**
         * Hierarchy Map Service
         * @param $log
         * @param mapModalService
         * @param mapService
         * @param $rootScope
         * @param $state
         * @param $timeout
         * @param localStorageService
         */
        function($log, mapModalService, mapService, $rootScope, $state, $timeout, localStorageService, modalService) {

            var mapData = undefined;
            var self = this;
            var _mapType = 'CUSTOMERS';
            var _tabs = [{
                "title": "performance",
                "active": "false"
            }, {
                "title": "comments",
                "active": "false"
            }, {
                "title": "bigideas",
                "active": "false"
            }, {
                "title": "media",
                "active": "false"
            }, {
                "title": "insight",
                "active": "false"
            }, {
                "title": "questions",
                "active": "false"
            }, {
                "title": "stageTouchpointMedia",
                "active": "false"
            }, {
                "title": "stageTouchpointinsight",
                "active": "false"
            }, ];

            var stages = undefined;
            var touchpoints = undefined;
            var actions = undefined;
            var actionsByChannel = undefined;
            var touchpointChannels = undefined;

            this.getStages = function() {
                return stages;
            };

            this.getTouchpoints = function() {
                return touchpoints;
            };

            this.getActions = function() {
                return actions;
            };


            this.getMapData = function() {
                return mapData;
            };

            this.getActionsByChannel = function() {
                return actionsByChannel;
            };

            this.getTouchpointChannels = function() {
                return touchpointChannels;
            }

            /**
             *
             * @param searchFn
             * @param type
             * @returns {*}
             */
            this.setMapData = function(searchFn, type) {
                return searchFn(type).then(function(dto) {
                    mapData = dto.data;

                    stages = [];
                    touchpoints = {};
                    actions = {};
                    actionsByChannel = _.reduce(mapData.channels, function(result, channel) {
                        result[channel.id] = {};
                        return result;
                    }, {});
                    touchpointChannels = {};

                    stages = _.map(mapData.stages, function(stage) {
                        touchpoints[stage.id] = _.map(stage.touchpoints, function(touchpoint) {
                            touchpointChannels[touchpoint.id] = [];

                            _.each(touchpoint.actions, function(action) {
                                action.$type = "ACTION";
                                action.$parent = touchpoint;
                                if (angular.isDefined(actionsByChannel[action.channel.id][touchpoint.id])) {
                                    actionsByChannel[action.channel.id][touchpoint.id].push(action);
                                } else {
                                    actionsByChannel[action.channel.id][touchpoint.id] = [action];
                                }

                                var foundChannel = _.find(touchpointChannels[touchpoint.id], {
                                    id: action.channel.id
                                });
                                if (!foundChannel) {
                                    touchpointChannels[touchpoint.id].push(action.channel);
                                }
                            });
                            actions[touchpoint.id] = touchpoint.actions;
                            touchpoint.$type = "TOUCHPOINT";
                            touchpoint.$parent = stage;
                            return touchpoint;
                        });
                        stage.$type = "STAGE";
                        return stage;
                    });

                    $rootScope.loader = false;
                    return mapData;
                }, function(err) {
                    console.error("Load map data unsuccessful", err);
                    $rootScope.loader = false;
                });
            };

            /**
             *
             * @returns {*}
             */
            this.addStage = function(addFn, searchFn) {
                var addModalInstance = mapModalService.addMapModal('Stage');
                addModalInstance.result.then(function(result) {
                    $rootScope.loader = true;
                    addFn(result.title).then(function(addResult) {
                        self.setMapData(searchFn);
                    }, function(err) {
                        $rootScope.loader = false;
                        console.error(err);
                    });
                });
            };

            /**
             * Edit Stage
             * @param stage
             * @param editFn
             * @param searchFn
             */
            this.editStage = function(stage, editFn, searchFn) {
                var addModalInstance = mapModalService.editMapModal(stage, "Stage");
                addModalInstance.result.then(function(modalResult) {
                    $rootScope.loader = true;
                    var dto = {
                        "stageId": stage.id,
                        "title": modalResult.title,
                        "positionIndex": stage.positionIndex
                    };
                    editFn(dto).then(function(editResult) {
                        self.setMapData(searchFn);
                    }, function(editErr) {
                        $rootScope.loader = false;
                    });
                });
            };

            this.updateStagePositionIndex = function(stage, editFn, searchFn) {
                $rootScope.loader = true;
                var dto = {
                    "stageId": stage.id,
                    "title": stage.title,
                    "positionIndex": stage.positionIndex
                };
                editFn(dto).then(function(editResult) {
                    self.setMapData(searchFn);
                }, function(editErr) {
                    $rootScope.loader = false;
                });
            };

            /**
             * Add TouchPoint
             * @param stage
             * @param addFn
             * @param searchFn
             */
            this.addTouchpoint = function(stage, addFn, searchFn) {
                var addModalInstance = mapModalService.addMapModal('Touchpoint');
                addModalInstance.result.then(function(modalResult) {
                    $rootScope.loader = true;
                    var dto = {
                        "stageId": stage.id,
                        "title": modalResult.title,
                        "positionIndex": stage.positionIndex
                    };
                    addFn(dto).then(function(addResult) {
                        self.setMapData(searchFn);
                    }, function(err) {
                        $rootScope.loader = false;
                        console.error(err);
                    });

                });
            };

            this.editTouchpoint = function(stage, touchpoint, editFn, searchFn) {
                var addModalInstance = mapModalService.editMapModal(touchpoint, "Touchpoint");
                addModalInstance.result.then(function(modalResult) {
                    $rootScope.loader = true;
                    var dto = {
                        "stageId": stage.id,
                        "title": modalResult.title,
                        "positionIndex": touchpoint.positionIndex,
                        "touchpointId": touchpoint.id
                    };
                    editFn(dto).then(function(editResult) {
                        self.setMapData(searchFn);
                    }, function(editErr) {
                        console.error(editErr);
                        $rootScope.loader = false;
                    });
                });
            };

            this.updateTouchpointPositionIndex = function(touchpoint, editFn, searchFn) {
                $rootScope.loader = true;
                var dto = {
                    "touchpointId": touchpoint.id,
                    "stageId": touchpoint.stageId,
                    "title": touchpoint.title,
                    "positionIndex": touchpoint.positionIndex
                };
                editFn(dto).then(function(editResult) {
                    self.setMapData(searchFn);
                }, function(editErr) {
                    $rootScope.loader = false;
                });
            };

            /**
             *
             * @param stage
             * @param touchpoint
             * @param addFn
             * @param searchFn
             * @param channels
             */
            this.addAction = function(stage, touchpoint, channels, addFn, searchFn) {
                var addModalInstance = mapModalService.addActionMapModal('Action', channels);
                addModalInstance.result.then(function(modalResult) {
                    $rootScope.loader = true;
                    var dto = {
                        "stageId": stage.id,
                        "title": modalResult.title,
                        "touchpointId": touchpoint.id,
                        "channelId": modalResult.channel.id
                    };
                    addFn(dto).then(function(addResult) {
                        self.setMapData(searchFn);
                    }, function(editErr) {
                        console.error(editErr);
                        $rootScope.loader = false;
                    });
                });
            };

            this.editAction = function(stage, touchpoint, action, channels, editFn, searchFn) {
                var addModalInstance = mapModalService.editActionMapModal(action, channels);
                addModalInstance.result.then(function(modalResult) {
                    $rootScope.loader = true;
                    var dto = {
                        "title": modalResult.title,
                        "positionIndex": action.positionIndex,
                        "stageId": stage.id,
                        "touchpointId": touchpoint.id,
                        "actionId": action.id,
                        "channelId": modalResult.channel.id
                    };
                    editFn(dto).then(function(editResult) {
                        self.setMapData(searchFn);
                    }, function(editErr) {
                        console.error(editErr);
                        $rootScope.loader = false;
                    });
                });
            };

            this.updateActionPositionIndex = function(action, editFn, searchFn) {
                $rootScope.loader = true;
                var dto = {
                    "channelId": action.channel.id,
                    "actionId": action.id,
                    "touchpointId": action.touchpointId,
                    "stageId": action.stageId,
                    "title": action.title,
                    "positionIndex": action.positionIndex
                };
                editFn(dto).then(function(editResult) {
                    self.setMapData(searchFn);
                }, function(editErr) {
                    $rootScope.loader = false;
                });
            };

            /**
             *
             * @param stage
             * @param touchPoint
             * @param action
             * @param deleteStageFn
             * @param deleteTouchpointFn
             * @param deleteActionFn
             * @param searchFn
             * @returns {*}
             */
            this.delete = function(stage, touchPoint, action, deleteStageFn, deleteTouchpointFn, deleteActionFn, searchFn) {
                var addModalInstance;
                if (angular.isDefined(stage.id) && touchPoint === undefined && action === undefined) {
                    addModalInstance = mapModalService.deleteMapModal('Stage', stage.positionIndex + 1);
                    addModalInstance.result.then(function() {
                        $rootScope.loader = true;
                        deleteStageFn(stage.id).then(function(deleteRes) {
                            self.setMapData(searchFn);
                        }, function(editErr) {
                            console.error(editErr);
                            $rootScope.loader = false;
                        });
                    });
                } else if (angular.isDefined(stage.id) && angular.isDefined(touchPoint.id) && action === undefined) {
                    addModalInstance = mapModalService.deleteMapModal('Touchpoint', (stage.positionIndex + 1) + '.' + (touchPoint.positionIndex + 1));
                    addModalInstance.result.then(function() {
                        $rootScope.loader = true;
                        deleteTouchpointFn(stage.id, touchPoint.id).then(function(deleteRes) {
                            self.setMapData(searchFn);
                        }, function(editErr) {
                            console.error(editErr);
                            $rootScope.loader = false;
                        });
                    });
                } else if (angular.isDefined(stage.id) && angular.isDefined(touchPoint.id) && angular.isDefined(action.id)) {
                    addModalInstance = mapModalService.deleteMapModal('Action', (stage.positionIndex + 1) + '.' + (touchPoint.positionIndex + 1) + '.' + (action.positionIndex + 1));
                    addModalInstance.result.then(function() {
                        $rootScope.loader = true;
                        deleteActionFn(stage.id, touchPoint.id, action.id).then(function(deleteRes) {
                            self.setMapData(searchFn);
                        }, function(editErr) {
                            console.error(editErr);
                            $rootScope.loader = false;
                        });
                    });
                }
            };

            this.setColor = function(expectationAverage, loyaltyAverage) {
                if ((expectationAverage === 0 && loyaltyAverage === 0) || (expectationAverage === undefined && loyaltyAverage === undefined)) {
                    return 'white';
                } else if (expectationAverage >= 1 && expectationAverage <= 2 && loyaltyAverage >= 4 && loyaltyAverage <= 5) {
                    return 'solidRed';
                } else if (expectationAverage >= 4 && expectationAverage <= 5 && loyaltyAverage >= 4 && loyaltyAverage <= 5) {
                    return 'solidGreen';
                } else {
                    return 'solidGray';
                }
            };

            this.moreCommentTxt = function(commentTxt) {
                var moreCommentModalInstance;
                moreCommentModalInstance = mapModalService.moreCommentTxt(commentTxt);
            };

            this.initMapType = function(feedbackSummary) {
                if (feedbackSummary) {
                    return feedbackSummary[_mapType];
                } else {
                    return _mapType;
                }
            };

            this.setMapType = function(mapType) {
                _mapType = mapType;
                self.initMapType();
            };

            this.getMapType = function(feedbackSummary) {
                return self.initMapType(feedbackSummary);
            };

            /**
             * @ngdoc function
             * @name addInsight
             * @description posting insight comments and files
             *
             */
            this.createInsight = function(type, stageId, touchpointId, actionId) {
                console.log(type, stageId, touchpointId, actionId);
                var addModalInstance = mapModalService.addCommentOrFileModal();
                stageId = type === 'stage' ? touchpointId : stageId;
                addModalInstance.result.then(function(modalResult) {
                    $rootScope.loader = true;
                    mapService.createInsight(type, modalResult, _mapType, stageId, touchpointId, actionId).then(function(insightRes) {
                        self.setMapData(mapService.getMapData, 'view');
                    }, function(insightErr) {
                        console.error(insightErr);
                        $rootScope.loader = false;
                    });
                });
            };

            this.createAdminUserMedia = function(type, stageId, touchpointId, actionId) {
                var addModalInstance = mapModalService.addFileModal();
                stageId = type === 'stage' ? touchpointId : stageId;
                addModalInstance.result.then(function(modalResult) {
                    $rootScope.loader = true;
                    mapService.createAdminUserMedia(type, modalResult, _mapType, stageId, touchpointId, actionId).then(function(mediaRes) {
                        $timeout(function() {
                            self.setMapData(mapService.getMapData, 'view');
                        }, 3000);
                    }, function(insightErr) {
                        console.error(insightErr);
                        $rootScope.loader = false;
                    });
                });

            };

            this.deleteInsight = function(type, insightid, stageId, touchpointId, actionId) {
                var delModalInstance = modalService.customDeleteModal('Insight', 'Are you sure you want to delete this item?');
                stageId = type === 'stage' ? touchpointId : stageId;
                delModalInstance.result.then(function() {
                    mapService.deleteInsight(type, insightid, stageId, touchpointId, actionId).then(function(insightRes) {
                        $rootScope.loader = true;
                        self.setMapData(mapService.getMapData, 'view');
                    }, function(insightErr) {
                        console.error(insightErr);
                        $rootScope.loader = false;
                    });
                });

            };

            /**
             * @ngdoc function
             * @name deleteComment
             * @description deletes a positive, neutral or negative comment
             *
             */
            this.deleteComment = function(stageId, touchpointId, actionId, answerId) {
                var delModalInstance = modalService.customDeleteModal('Comment', 'Are you sure you want to delete this item?');
                delModalInstance.result.then(function() {
                    mapService.deleteComment(stageId, touchpointId, actionId, answerId).then(function(delCommRes) {
                        $rootScope.loader = true;
                        self.setMapData(mapService.getMapData, 'view');
                    }, function(delCommErr) {
                        console.error(delCommErr);
                        $rootScope.loader = false;
                    });
                });
            };

            /**
             * @ngdoc function
             * @name deleteBigIdea
             * @description deletes a big idea
             *
             */
            this.deleteBigIdea = function(stageId, touchpointId, actionId, answerId) {
                var delModalInstance = modalService.customDeleteModal('Big Idea', 'Are you sure you want to delete this item?');
                delModalInstance.result.then(function() {
                    mapService.deleteBigIdea(stageId, touchpointId, actionId, answerId).then(function(delBigIdeaRes) {
                        $rootScope.loader = true;
                        self.setMapData(mapService.getMapData, 'view');
                    }, function(delBigIdeaErr) {
                        console.error(delBigIdeaErr);
                        $rootScope.loader = false;
                    });
                });
            };

            /**
             * @ngdoc function
             * @name deleteMedia
             * @description deletes a media
             *
             */
            this.deleteMedia = function(stageId, touchpointId, actionId, answerId, type, obj) {

                var delModalInstance = modalService.customDeleteModal('Media', 'Are you sure you want to delete this item?');
                delModalInstance.result.then(function() {
                    if (obj.creatorType == "adminUser") {

                        mapService.deleteAdminUserMedia(stageId, touchpointId, actionId, obj.id, type).then(function(delMediaRes) {
                            $rootScope.loader = true;
                            self.setMapData(mapService.getMapData, 'view');
                        }, function(delMediaErr) {
                            console.error(delMediaErr);
                            $rootScope.loader = false;
                        });
                    } else {

                        mapService.deleteMedia(stageId, touchpointId, actionId, answerId, type, obj.missionType).then(function(delMediaRes) {
                            $rootScope.loader = true;
                            self.setMapData(mapService.getMapData, 'view');
                        }, function(delMediaErr) {
                            console.error(delMediaErr);
                            $rootScope.loader = false;
                        });
                    }

                });
            };


            /**
             * @ngdoc function
             * @name promoteMedia
             * @description promote or demotes a media
             *
             */
            this.promoteMedia = function(stageId, touchpointId, actionId, answerId, type, obj) {

                if (obj.creatorType == "adminUser") {
                    $rootScope.loader = true;
                    mapService.promoteAdminUserMedia(type, angular.isUndefined(obj.promotedDate), obj.id, stageId, touchpointId, actionId).then(function(promoteMediaRes) {
                        self.setMapData(mapService.getMapData, 'view');
                    }, function(promoteMediaErr) {
                        console.error(promoteMediaErr);
                        $rootScope.loader = false;
                    });
                } else {
                    $rootScope.loader = true;
                    mapService.promoteAnswer(type, 'media', answerId, angular.isUndefined(obj.promotedDate), stageId, touchpointId, actionId, obj.missionType).then(function(promoteMediaRes) {
                        self.setMapData(mapService.getMapData, 'view');
                    }, function(promoteMediaErr) {
                        console.error(promoteMediaErr);
                        $rootScope.loader = false;
                    });
                }
            };

            this.promoteComment = function(stageId, touchpointId, actionId, answerId, type, obj) {
                $rootScope.loader = true;
                mapService.promoteAnswer(type, 'comment', answerId, (obj.promotedDate === null), stageId, touchpointId, actionId).then(function(promoteAnsRes) {
                    self.setMapData(mapService.getMapData, 'view');
                }, function(promoteAnsErr) {
                    console.error(promoteAnsErr);
                    $rootScope.loader = false;
                });
            };

            this.promoteBigIdea = function(stageId, touchpointId, actionId, answerId, type, obj) {
                $rootScope.loader = true;
                mapService.promoteAnswer(type, 'bigidea', answerId, (obj.promotedDate === null), stageId, touchpointId, actionId).then(function(promoteAnsRes) {
                    self.setMapData(mapService.getMapData, 'view');
                }, function(promoteAnsErr) {
                    console.error(promoteAnsErr);
                    $rootScope.loader = false;
                });
            };

            this.promoteInsight = function(type, insightid, obj, stageId, touchpointId, actionId) {
                $rootScope.loader = true;
                stageId = type === 'stage' ? touchpointId : stageId;
                mapService.promoteInsight(type, (obj.promotedDate === null), insightid, stageId, touchpointId, actionId).then(function(insightRes) {
                    self.setMapData(mapService.getMapData, 'view');
                }, function(insightErr) {
                    console.error(insightErr);
                    $rootScope.loader = false;
                });
            };

            this.getTabs = function() {
                return _tabs;
            };

            this.setActiveTab = function(activeTab) {
                angular.forEach(_tabs, function(tab, key) {
                    tab.active = !!angular.equals(tab.title, activeTab);
                });
            };

            this.getActiveTab = function(activeTab) {
                var activeTab = undefined;
                angular.forEach(_tabs, function(tab, key) {
                    if (tab.active) {
                        activeTab = tab.title;
                    }
                });
                return activeTab;
            };

            this.getFileExtension = function(fileURL) {
                var ext = fileURL.split('.').pop();
                var extToLowercase = angular.lowercase(ext);

                return extToLowercase;
            };

            this.getFileType = function(fileURL) {
                var ext = self.getFileExtension(fileURL);
                var fileType;

                if (ext == 'aaf' || ext == '3gp' || ext == 'avi' || ext == 'mp4' || ext == 'wmv' || ext == 'mov' || ext == 'm4v') {
                    fileType = 'video';
                } else if (ext == 'mp3' || ext == 'wma' || ext == 'cda' || ext == 'wav' || ext == 'amr' || ext == 'ima4') {
                    fileType = 'audio';
                } else if (ext == 'jpg' || ext == 'gif' || ext == 'jpeg' || ext == 'png' || ext == 'ico' || ext == 'svg' || ext == 'tiff') {
                    fileType = 'image';
                } else {
                    fileType = 'file';
                }

                return fileType;
            };

            this.setQuestions = function(questions, currentPage, numPerPage) {
                var filteredQuestions,
                    begin = ((currentPage - 1) * numPerPage) || 0,
                    end = begin + numPerPage;
                if (angular.isDefined(questions)) {
                    filteredQuestions = questions.slice(begin, end);
                }
                return filteredQuestions;
            };

            this.getQuestions = function(questions, page, numPerPage) {
                return self.setQuestions(questions, page, numPerPage);
            };

            this.sortByModalData = function(data) {
                var val;
                if (data.createdDate !== null && data.createdDate !== undefined) {
                    val = new Date() - data.createdDate;
                } else {
                    val = new Date() - data.lastModifiedDate;
                }
                return val;
            };
        }
    ]);

})(angular.module('aet-directives-hierarchyMap'));
