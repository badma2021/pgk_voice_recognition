
import { Component } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { SubCategoryEditService } from '../_services/sub-category-edit.service';
import { SubCategory } from '../types/subCategory';

import { Router } from '@angular/router';

@Component({
  selector: 'app-sub-category-edit',
  templateUrl: './sub-category-edit.component.html',
  styleUrls: ['./sub-category-edit.component.css']
})
export class SubCategoryEditComponent {
  subCategories: SubCategory[] = []
  editedId: number|null = null
  editedName = ''
  newSubCategoryName = ''
  userId = null
  isSaving = false;
  constructor(private subCategoryEditService: SubCategoryEditService, private tokenStorage: TokenStorageService, private router: Router) {}


  ngOnInit() {
  this.userId = this.tokenStorage.getUser().userId;
    this.loadSubCategories(this.userId)
  }

  loadSubCategories(userId: number) {
    this.subCategoryEditService.getAll(this.userId)
      .subscribe(res => this.subCategories = res)
  }

  startEdit(cat: SubCategory) {
    this.editedId = cat.id
    this.editedName = cat.subCategoryName
  }

saveEdit(cat: SubCategory) {
  // валидация
  if (!this.editedName?.trim()) { return; }

  // получить userId заранее
  this.userId = this.tokenStorage.getUser().userId;

  const updated = { id: cat.id, subCategoryName: this.editedName };

  this.isSaving = true;
  this.subCategoryEditService.update(updated).subscribe({
    next: () => {
      this.loadSubCategories(this.userId);
      this.cancelEdit();
    },
    error: (err) => {
      // показать ошибку пользователю (toast/snackbar)
      console.error('Update failed', err);
    },
    complete: () => {
      this.isSaving = false;
    }
  });
}

cancelEdit() {
  if (this.isSaving) { return; } // опционально блокируем отмену во время сохранения
  this.editedId = null;
  this.editedName = '';
}

  addSubCategory() {
    const subCategory = {
      id: 0,
      subCategoryName: this.newSubCategoryName,
      userId: this.userId
    }
  this.userId = this.tokenStorage.getUser().userId;
    this.subCategoryEditService.create(subCategory)
      .subscribe(() => this.loadSubCategories(this.userId))

    this.newSubCategoryName = ''
  }
}
