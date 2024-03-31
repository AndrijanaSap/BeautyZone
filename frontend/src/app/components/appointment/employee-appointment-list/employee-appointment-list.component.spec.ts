import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeAppointmentListComponent } from './employee-appointment-list.component';

describe('EmployeeAppointmentListComponent', () => {
  let component: EmployeeAppointmentListComponent;
  let fixture: ComponentFixture<EmployeeAppointmentListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmployeeAppointmentListComponent]
    });
    fixture = TestBed.createComponent(EmployeeAppointmentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
