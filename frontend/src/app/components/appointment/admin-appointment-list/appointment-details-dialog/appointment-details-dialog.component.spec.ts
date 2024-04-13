import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentDetailsDialogComponent } from './appointment-details-dialog.component';

describe('AppointmentDetailsDialogComponent', () => {
  let component: AppointmentDetailsDialogComponent;
  let fixture: ComponentFixture<AppointmentDetailsDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AppointmentDetailsDialogComponent]
    });
    fixture = TestBed.createComponent(AppointmentDetailsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});