import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminServiceAddComponent } from './admin-service-add.component';

describe('AdminServiceAddComponent', () => {
  let component: AdminServiceAddComponent;
  let fixture: ComponentFixture<AdminServiceAddComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminServiceAddComponent]
    });
    fixture = TestBed.createComponent(AdminServiceAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
