$(function() {
	

function Department(data) {
    this.name = ko.observable(data.name);
    this.empcount = ko.observable(data.empcount);
    this.id = ko.observable(data.id);
}

function Employee(data) {
	this.id = ko.observable(data.id);
	this.firstname = ko.observable(data.firstname);
    this.lastname = ko.observable(data.lastname);
    this.department = ko.observable(data.department);
}

function DepartmentListViewModel() {
    // Data
    var self = this;
    self.employees = ko.observableArray([]);
    self.showEmployees = ko.computed(function() {
    	return (self.employees() != undefined) && self.employees().length > 0; 
    });
    self.departments = ko.observableArray([]);
    self.newDepartmentText = ko.observable();
    
    self.chosenDepartmentId = ko.observable();
    self.chosenDepartmentName = ko.computed(function() {
    	if (self.chosenDepartmentId() == undefined) return undefined;
        var dept = self.departments().filter(function(item) { return item.id() == self.chosenDepartmentId() });
        return dept.length == 1 ? dept[0].name() : null;
    });

    self.selectedDepartmentView = ko.observable(null);
    self.getSelectedDepartmentView = function() {
    	if (self.chosenDepartmentId() == undefined) return null;
    	return self.selectedDepartmentView(); 
    }
    
    // Operations
    self.clickDepartment = function(dept) { 
    	location.hash = "department" + "/" + dept.id();
    };
    
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
    
    self.removeEmployee = function(emp) { 
        $.ajax("/api/delete-employee", {
            type: "POST",
        	data: ko.toJSON(emp.id),
            contentType: "application/json",
            success: function(result) { 
            	self.employees.remove(emp);
            	self.loadDepartmentList(); // should load only the affected department empcount
            }
        });
    };
    
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
    
    self.clickAddEmployee = function() {
    	location.hash = "department" + "/" + self.chosenDepartmentId() + "/addemployee";
    };
    
    self.firstname = ko.observable();
    self.lastname = ko.observable();
    
    self.addEmployee = function() {
    	var json = { firstname: self.firstname(), 
    			     lastname: self.lastname(),
    			     department: parseInt(self.chosenDepartmentId())}
    	
        $.ajax("/api/add-employee", {
            type: "POST",
        	data: ko.toJSON(json),
            contentType: "application/json",
            success: function(result) { 
            	json.id = parseInt(result);
                self.employees.push(new Employee(json));
                self.firstname("");
                self.lastname("");
                location.hash = "department" + "/" + json.department;
            }
        });
    };
    
    self.loadDepartmentList = function() {
    	// Load initial state from server
	    $.getJSON("/api/departments", function(allData) {
	        var depts = $.map(allData, function(item) { return new Department(item) });
	        self.departments(depts);
	    });  
    }
    
    function loadEmployees(departmentId) {
    	$.getJSON("/api/department/" + departmentId + "/employees", 
	        function(allData) {
	            var emps = $.map(allData, function(item) { return new Employee(item) });
	            self.employees(emps);
	        }
    	);
    }
    
    // Client-side routes    
    Sammy(function() {
    	this.get('/', function() {
    		self.chosenDepartmentId(null);
    		self.loadDepartmentList();
    	});
    	
        this.get('#department/:deptId', function() {
            self.chosenDepartmentId(this.params.deptId);
            self.selectedDepartmentView("list");
            // bookmarked entrance would leave departments uninitialized
            self.loadDepartmentList();
    	    loadEmployees(this.params.deptId);  
        });
        
        this.get('#department/:deptId/addemployee', function() {
            self.chosenDepartmentId(this.params.deptId);
            self.selectedDepartmentView("addemployee");
            // bookmarked entrance would leave departments uninitialized
            self.loadDepartmentList();
            self.employees([]);
        });

    }).run();
}

ko.applyBindings(new DepartmentListViewModel());

});