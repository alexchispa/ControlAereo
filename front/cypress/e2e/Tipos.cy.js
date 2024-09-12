describe('Tipo Page', () => {
  beforeEach(() => {
    cy.visit('http://localhost:5173/tipo')
  })

  it('Mostrar listado Tipos', () => {
    cy.get('.tipo-container').should('exist')
    cy.get('table.custom-table tbody tr').should('have.length.greaterThan', 0)
  })

  it('Ver formulario de creacion Tipo', () => {
    cy.get('.create-button').click()
    cy.url().should('include', '/tipoFormulario')
    cy.get('h1').should('contain', 'Nuevo Tipo')
  })

  it('Crear Tipo', () => {
    cy.get('.create-button').click()
    cy.url().should('include', '/tipoFormulario')
    cy.get('input[name="nombre"]').type('Nuevo Tipo')
    cy.get('input[name="modelo"]').type('Modelo del nuevo tipo')
    cy.get('input[name="fabricante"]').type('Fabricante del nuevo tipo')
    cy.get('input[name="capacidad"]').type('100')
    cy.get('.submit-button').click()
    cy.url().should('include', '/tipo')
    cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Tipo creado exitosamente')
    cy.get('table.custom-table tbody tr').should('contain', 'Nuevo Tipo')
  })

  it('Ver formulario de Actualizacion Tipo', () => {
    cy.get('table.custom-table tbody tr').first().find('.action-button').contains('Editar').click({ force: true }) // Forzar el clic
    cy.url().should('include', '/tipoForm')
  })

  it('Actualizar Tipo', () => {
    cy.get('table.custom-table tbody tr').first().find('.action-button').contains('Editar').click({ force: true }) // Forzar el clic
    cy.url().should('include', '/tipoForm')
    cy.get('input[name="nombre"]').clear().type('Tipo Actualizado')
    cy.get('input[name="modelo"]').clear().type('Modelo actualizado del tipo')
    cy.get('input[name="fabricante"]').clear().type('Fabricante actualizado del tipo')
    cy.get('input[name="capacidad"]').clear().type('200')
    cy.get('.submit-button').click()
    cy.get('.confirmation-dialog .submit-button', { timeout: 10000 }).should('be.visible').click()
    cy.url().should('include', '/tipo')
    cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Tipo actualizado exitosamente')
    cy.get('table.custom-table tbody tr').first().should('contain', 'Tipo Actualizado')
  })

  it('Se puede borrar un Tipo sin naves', () => {
    cy.get('table.custom-table tbody tr').contains('Nuevo Tipo').parent().find('.delete-button').click({ force: true }) // Forzar el clic
    cy.get('.delete-dialog .confirm-button', { timeout: 10000 }).should('be.visible').click()
    cy.url().should('include', '/tipo')
    cy.get('.modal-aviso', { timeout: 10000 }).should('contain', 'Tipo eliminado exitosamente')
  })
})