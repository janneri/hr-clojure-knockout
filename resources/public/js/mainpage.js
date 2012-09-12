$(function() {
	

function Department(data) {
    this.name = ko.observable(data.name);
    this.empcount = ko.observable(data.empcount);
    this.id = ko.observable(data.id);
}

function DepartmentListViewModel() {
    // Data
    var self = this;
    self.departments = ko.observableArray([]);
    self.newDepartmentText = ko.observable();

    // Operations
    self.removeDepartment = function(department) { 
        $.ajax("/api/delete-department", {
            type: "POST",
        	data: ko.toJSON(department.id),
            contentType: "application/json",
            success: function(result) { 
            	self.departments.remove(department);
            }
        });
    };
    
    // Load initial state from server
    $.getJSON("/api/departments", function(allData) {
        var depts = $.map(allData, function(item) { return new Department(item) });
        self.departments(depts);
    });  
    
    self.addDepartment = function() {
    	var deptJson = { name: this.newDepartmentText() }
    	
        $.ajax("/api/add-department", {
            type: "POST",
        	data: ko.toJSON(deptJson),
            contentType: "application/json",
            success: function(result) { 
            	deptJson.id = parseInt(result);
            	deptJson.empcount = 0;
                self.departments.push(new Department(deptJson));
                self.newDepartmentText("");
            }
        });
    }; 
    
}

ko.applyBindings(new DepartmentListViewModel());

});