import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAppointmentListComponent } from './admin-appointment-list.component';

describe('AdminAppointmentListComponent', () => {
  let component: AdminAppointmentListComponent;
  let fixture: ComponentFixture<AdminAppointmentListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminAppointmentListComponent]
    });
    fixture = TestBed.createComponent(AdminAppointmentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
