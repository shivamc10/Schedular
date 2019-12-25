WRMCB=function(e){var c=console;if(c&&c.log&&c.error){c.log('Error running batched script.');c.error(e);}}
;
try {
/* module-key = 'com.stiltsoft.confluence.plugin.tablefilter.tablefilter:macro-inserter-resources', location = 'soy/macro-inserter.soy' */
// This file was automatically generated from macro-inserter.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace TFInserterTamplate.
 */

if (typeof TFInserterTamplate == 'undefined') { var TFInserterTamplate = {}; }


TFInserterTamplate.panel = function(opt_data, opt_ignored) {
  var output = '<div class="tfac-macro-inserter">';
  var macroList4 = opt_data.macros;
  var macroListLen4 = macroList4.length;
  for (var macroIndex4 = 0; macroIndex4 < macroListLen4; macroIndex4++) {
    var macroData4 = macroList4[macroIndex4];
    output += '<span class="aui-icon aui-icon-small ' + soy.$$escapeHtml(macroData4.name) + '-inserter" title="' + TFInserterTamplate.title({macro: macroData4.id}) + '" data-macro="' + soy.$$escapeHtml(macroData4.name) + '"></span>';
  }
  output += '<a target="_blank" ' + ((opt_data.isAdmin) ? ' href="' + soy.$$escapeHtml("") + '/admin/plugins/tfac/config.action" ' : ' href="' + soy.$$escapeHtml("") + '/users/tfac-settings.action" ') + '><span class="aui-icon aui-icon-small aui-iconfont-configure" title="' + soy.$$escapeHtml('Configure visibility') + '"></span></a></div>';
  return output;
};
if (goog.DEBUG) {
  TFInserterTamplate.panel.soyTemplateName = 'TFInserterTamplate.panel';
}


TFInserterTamplate.title = function(opt_data, opt_ignored) {
  return '' + ((opt_data.macro == 'tf') ? soy.$$escapeHtml('Filter table data') : (opt_data.macro == 'pivot') ? soy.$$escapeHtml('Create a pivot table') : soy.$$escapeHtml('Create a chart from data series'));
};
if (goog.DEBUG) {
  TFInserterTamplate.title.soyTemplateName = 'TFInserterTamplate.title';
}

}catch(e){WRMCB(e)};
;
try {
/* module-key = 'com.stiltsoft.confluence.plugin.tablefilter.tablefilter:macro-inserter-resources', location = 'js/macro-inserter.js' */
(function($) {

    var TFACInserter = {

        ignoreMacros: ['.quiz-settings-content', '.code', '.profile-macro', '.gallery', '#course-content'],
        tfacSources: [
            {name: 'table-joiner', class: 'tj-source'},
            {name: 'pivot-table', class: 'original-table'},
            {name: 'table-chart', class: 'chart-body'}
            ],
        tfacMacros: [
            {name: 'table-filter', class: 'tablefilter-outer-wrapper', id: 'tf'},
            {name: 'pivot-table', class: 'pivot-content', id: 'pivot'},
            {name: 'table-joiner', class: 'table-joiner', id: 'tj'},
            {name: 'table-chart', class: 'table-chart-contents', id: 'chart'}
            ],
        macrosWithBody: ['json-table', 'csv', 'vote', 'survey', 'report-table', 'confiform-table'],
        macroDuplications: ['.pocketquery-dynamic-load-container'],

        init: function () {
            var self = this;
            self.content = $('#main-content');
            self.tables = self.content.find('table');

            self.tables.each(function () {
                self.initTable($(this));
            });

            self.content.delegate('table', 'mouseover.tf-init', function () {
                var table = $(this);
                table.undelegate('mouseover.tf-init');
                if (self.tables.index(table) === -1) {
                    self.initTable(table);
                }
            });
        },

        initTable: function (table) {
            var self = TFACInserter;

            if (table.parents(self.ignoreMacros.join(',')).length) return;

            var params = {table: table, containerType: 'macro', macros: JSON.parse(JSON.stringify(self.tfacMacros))};

            var parents = table.parents('.' + self.tfacMacros.map(function (v) { return v.class }).concat(self.tfacSources.map(function (v) { return v.class })).join(',.'));
            parents.each(function () {
                var parent = $(this);
                if (parent.is('.' + self.tfacSources.map(function (v) { return v.class }).join(',.'))) {
                    self.setMacroUsage(self.tfacSources, parent, params);
                    params.containerType = 'macro-body';
                    return false;
                }
                self.setMacroUsage(self.tfacMacros, parent, params);
            });

            var macrosToInsert = params.macros.filter(function (m) { return !m.present && m.name !== 'table-joiner' });
            if (macrosToInsert.length) {
                table.bind('mouseover.tf', function() {
                    clearTimeout(self.timer);

                    if (!table.is(self.activeTable)) {
                        self.timer = setTimeout(function() {
                            if (self.panel) {
                                self.panel.remove();
                            }

                            self.activeTable = table;

                            self.panel = $(TFInserterTamplate.panel({macros: macrosToInsert, isAdmin: AJS.params.isConfluenceAdmin}));
                            self.content.append(self.panel);

                            var offset = table.offset();
                            var parent = table.parent();
                            self.panel.offset({top: offset.top, left: Math.max(offset.left, parent.offset().left) + Math.min(table.width(), parent.width()) + 5});
                            self.bindPanel(params);
                            table.unbind('mouseleave.tf').bind('mouseleave.tf', self.removePanel);
                        }, 150);
                    }
                });
            }
        },

        bindPanel: function (params) {
            var self = TFACInserter;

            self.panel.bind('mouseenter.tf', function() {
                clearTimeout(self.timer);
            }).bind('mouseleave.tf', self.removePanel);

            self.panel.children('span').click(function () {
                if (self.panel.hasClass('disabled')) return;

                self.panel.addClass('disabled');
                var btn = $(this);
                var macroName = btn.data('macro');
                var containerType;
                var container;
                var number;
                var tableNumber = -1;

                var TEI = params.table.parents('[data-macro-name="table-excerpt-include"]');
                
                if (TEI.length) {
                    container = "table-excerpt-include";
                    containerType = 'macro';
                    number = self.content.find('[data-macro-name="table-excerpt-include"]').index(TEI)
                } else if (params.owner) {
                    container = params.owner.name;
                    containerType = params.containerType;
                    number = self.filterTEI(self.content.find('.' + params.owner.class)).index(params.owner.node);
                } else {
                    var wrapperMacro = params.table.parents('.conf-macro, .jira-table').eq(0);
                    if (params.table.attr('data-macro-name')) {
                        containerType = 'macro';
                        container = params.table.data('macro-name');
                        number = self.filterTEI(self.content.find('[data-macro-name="' + container + '"]')).index(params.table);
                    } else if (wrapperMacro.length) {
                        if (wrapperMacro.hasClass('jira-table')) {
                            container = 'jira';
                            number = self.filterTEI(self.content.find('.jira-table')).index(wrapperMacro);
                        } else {
                            container = wrapperMacro.data('macro-name');
                            number = self.filterTEI(self.content.find('[data-macro-name="' + container + '"]').not(self.macroDuplications.join(','))).index(wrapperMacro);
                        }
                        if (wrapperMacro.attr('data-hasbody') === 'true' && self.macrosWithBody.indexOf(container) === -1) {
                            containerType = 'macro-body';
                            tableNumber = wrapperMacro.find(':not(.conf-macro) table').index(params.table);
                        } else {
                            containerType = 'macro';
                        }
                    } else if (params.table.hasClass('appfire-table-plus-parms')) {
                        containerType = 'macro';
                        container = 'json-table|csv|sql-query';
                        number = self.content.find('div.table-wrap > table.confluenceTable.appfire-table-plus-parms').filter(function () {
                            return self.content.find('table.confluenceTable.appfire-table-plus-parms table.confluenceTable.appfire-table-plus-parms').index(this) === -1;
                        }).index(params.table);
                    } else {
                        containerType = 'table';
                        number = self.content.find('div.table-wrap > table.confluenceTable:not(.appfire-table-plus-parms)').filter(function () {
                            return $(this).parents('.conf-macro').length === 0;
                        }).index(params.table);
                    }
                }

                $.ajax({
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    type: 'POST',
                    async: false,
                    cache: false,
                    url: Confluence.getContextPath() + '/rest/table-filter/1.0/service/insert',
                    dataType: 'json',
                    data: {
                        pageId: self.getPageId(),
                        pageVersion: $('meta[name="page-version"]').attr('content'),
                        macroName: macroName,
                        containerType: containerType,
                        container: container,
                        number: number,
                        tableNumber: tableNumber
                    },
                    success: function () {
                        location.reload();
                    },
                    error: function (xhr) {
                        var isReadOnly = xhr.responseText.indexOf('com.atlassian.confluence.api.service.exceptions.ReadOnlyException') !== -1;
                        var messageContainer = $('<div id="tfac-inserter-message"></div>');
                        self.content.append(messageContainer);
                        AJS.messages.error(messageContainer, {
                            body: isReadOnly ? "This Confluence site is temporarily in the read-only mode. You cannot make any changes right now." : "Oops! It seems that the macro cannot be added. Please edit the page and add this macro",
                            closeable: true
                        });
                        setTimeout(function () {
                            messageContainer.remove();
                        }, 5000);
                        self.panel.removeClass('disabled');
                    }
                });
            })
        },

        removePanel: function() {
            var self = TFACInserter;

            clearTimeout(self.timer);

            self.timer = setTimeout(function() {
                self.activeTable = null;
                if (self.panel) {
                    self.panel.fadeOut(50, function(){
                        self.panel.remove();
                        self.panel = null;
                    });
                }
            }, 300);
        },

        setMacroUsage: function (descriptors, parent, params) {
            descriptors.some(function (v) {
                if (parent.hasClass(v.class)) {
                    params.macros.filter(function (m) { return m.name === v.name })[0].present = true;
                    params.owner = {
                        name: v.name,
                        node: parent,
                        class: v.class
                    };
                    return true;
                }
            });
        },

        filterTEI: function(elems) {
            return elems.filter(function () {
                return $(this).parents('[data-macro-name="table-excerpt-include"]').length === 0;
            });
        },

        getPageId: function () {
            return AJS.params.contentId || AJS.Meta.get("content-id") || AJS.params.pageId || AJS.Meta.get("page-id");
        }
    };

    AJS.toInit(function() {
        if ($('#editPageLink').length) {
            TFACInserter.init();
        }
    });

})(AJS.$);



}catch(e){WRMCB(e)};